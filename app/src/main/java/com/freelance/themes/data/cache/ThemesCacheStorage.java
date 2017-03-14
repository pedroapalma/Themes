package com.freelance.themes.data.cache;

import com.freelance.themes.data.cache.serializer.JsonSerializer;
import com.freelance.themes.data.entity.ThemeEntity;
import com.freelance.themes.data.exception.NotFoundException;
import com.freelance.themes.data.exception.UnexpectedException;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import rx.Observable;

public class ThemesCacheStorage implements DataCacheStorage<List<ThemeEntity>> {

  public static final String KEY = "key_themes";

  private CacheStorage cacheStorage;
  private Executor executor;
  private JsonSerializer jsonSerializer;
  private boolean isSaved;

  @Inject
  public ThemesCacheStorage(CacheStorage cacheStorage, Executor executor, JsonSerializer jsonSerializer) {
    this.cacheStorage = cacheStorage;
    this.executor = executor;
    this.jsonSerializer = jsonSerializer;
  }

  @Override
  public Observable<List<ThemeEntity>> getObservable() {
    return Observable.create(subscriber -> {
      try {
        String themes = cacheStorage.get(KEY);
        if (themes != null) {
          subscriber.onNext(jsonSerializer.deserializeList(themes, ThemeEntity.class));
          subscriber.onCompleted();
        } else {
          subscriber.onError(new NotFoundException());
        }
      } catch (Exception e) {
        subscriber.onError(new UnexpectedException(e.getMessage(), e.getCause()));
      }
    });
  }

  @Override
  public List<ThemeEntity> get() {
    List<ThemeEntity> themeEntityList;
    try {
      String themes = cacheStorage.get(KEY);
      if (themes != null)
        themeEntityList = jsonSerializer.deserializeList(themes, ThemeEntity.class);
      else
        return null;
    } catch (Exception e) {
      return null;
    }
    return themeEntityList;
  }

  @Override
  public void save(List<ThemeEntity> data) {
    if (data != null) {
      String jsonString = jsonSerializer.serialize(data);
      Runnable runnable = new LocalStorageWriter(cacheStorage, jsonString);
      executeAsynchronously(runnable);
    }
  }

  @SuppressWarnings("unused")
  @Override
  public boolean clear() {
      return exists() && cacheStorage.clear(KEY);
  }

  @Override
  public boolean exists() {
    return cacheStorage.exists(KEY);
  }

  private void executeAsynchronously(Runnable runnable) {
    this.executor.execute(runnable);
  }

  private class LocalStorageWriter implements Runnable {
    private CacheStorage cacheStorage;
    private String data;

    LocalStorageWriter(CacheStorage cacheStorage, String data) {
      this.cacheStorage = cacheStorage;
      this.data = data;
    }

    @Override public void run() {
      isSaved = cacheStorage.save(KEY, data);
    }
  }

  public boolean isSaved() {
    return isSaved;
  }
}
