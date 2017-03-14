package com.freelance.themes.di.module;

import com.freelance.themes.data.cache.DataCacheStorage;
import com.freelance.themes.data.exception.NotFoundException;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Observable;

@Module
public class CacheModule {

  public static final String UNEXPECTED_EXCEPTION = "unexpected_exception";
  public static final String NOT_FOUND_EXCEPTION = "not_found_exception";
  public static final String SUCCESS_CASE = "success_case";

  private String type;

  public CacheModule(String type) {
    this.type = type;
  }

  @Provides
  @Singleton
  public DataCacheStorage providesThemesCacheStorage() {
    MockThemesCacheStorage mockThemesCacheStorage = new MockThemesCacheStorage();
    if (type.equals(NOT_FOUND_EXCEPTION)) {
      mockThemesCacheStorage.setExists(true);
      mockThemesCacheStorage.setDummyCacheThemesResult(Observable.error(new NotFoundException()));
    } else if (type.equals(UNEXPECTED_EXCEPTION)) {
      mockThemesCacheStorage.setExists(true);
      mockThemesCacheStorage.setDummyCacheThemesResult(Observable.error(new Exception()));
    } else if (type.equals(SUCCESS_CASE))
      mockThemesCacheStorage.setExists(true);
    return mockThemesCacheStorage;
  }
}
