package com.freelance.themes.di.module;

import com.freelance.themes.data.cache.CacheStorage;
import com.freelance.themes.data.cache.DataCacheStorage;
import com.freelance.themes.data.cache.ThemesCacheStorage;
import com.freelance.themes.data.cache.serializer.JsonSerializer;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {

  @Provides
  @Singleton
  public DataCacheStorage providesThemesCacheStorage(CacheStorage cacheStorage, Executor executor,
      JsonSerializer jsonSerializer) {
    return new ThemesCacheStorage(cacheStorage, executor,  jsonSerializer);
  }
}
