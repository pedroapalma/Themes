package com.freelance.themes.data.repository.datasource;

import com.freelance.themes.data.cache.DataCacheStorage;
import com.freelance.themes.data.entity.ThemeEntity;
import com.freelance.themes.data.repository.ThemeService;

import java.util.List;

import rx.Observable;

public class CacheThemeService implements ThemeService {

  private DataCacheStorage themesCacheStorage;

  public CacheThemeService(DataCacheStorage themesCacheStorage) {
    this.themesCacheStorage = themesCacheStorage;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Observable<List<ThemeEntity>> getThemeList() {
    return themesCacheStorage.getObservable();
  }
}
