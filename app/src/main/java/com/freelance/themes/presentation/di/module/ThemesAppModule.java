package com.freelance.themes.presentation.di.module;

import android.content.Context;

import com.freelance.themes.data.api.RedditApiService;
import com.freelance.themes.data.api.mapper.ApiDataMapper;
import com.freelance.themes.data.api.mapper.ApiMapper;
import com.freelance.themes.data.cache.CacheStorage;
import com.freelance.themes.data.cache.CacheStorageImp;
import com.freelance.themes.data.cache.DataCacheStorage;
import com.freelance.themes.data.cache.serializer.JsonSerializer;
import com.freelance.themes.data.repository.ThemeRepository;
import com.freelance.themes.data.repository.ThemeRepositoryFactory;
import com.freelance.themes.data.repository.ThemeRepositoryImp;
import com.freelance.themes.data.repository.ThemeService;
import com.freelance.themes.data.repository.datasource.ApiThemeService;
import com.freelance.themes.data.repository.datasource.CacheThemeService;
import com.freelance.themes.presentation.ThemesApp;
import com.freelance.themes.presentation.di.scope.Named;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ThemesAppModule {

  ThemesApp app;

  public ThemesAppModule(ThemesApp app) {
    this.app = app;
  }

  @Provides
  @Singleton
  Context provideApplicationContext() {
    return this.app;
  }

  @Provides
  @Singleton
  ApiMapper providesApiDataMapper() {
    return new ApiDataMapper();
  }

  @Provides
  @Singleton
  public JsonSerializer providesJsonSerializer() {
    return new JsonSerializer();
  }

  @Provides
  @Singleton
  public CacheStorage providesCacheStorage(Context context) {
    return new CacheStorageImp(context);
  }

  @Provides
  @Singleton
  public Executor providesExecutor() {
    return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
  }

  @Provides
  @Singleton
  @Named("apiThemeService")
  public ThemeService providesApiThemeService(RedditApiService redditApiService, ApiMapper apiMapper,
      DataCacheStorage themeCacheStorage) {
    return new ApiThemeService(redditApiService, apiMapper, themeCacheStorage);
  }

  @Provides
  @Singleton
  @Named("cacheThemeService")
  public ThemeService providesCacheThemeService(DataCacheStorage themeCacheStorage) {
    return new CacheThemeService(themeCacheStorage);
  }

  @Provides
  @Singleton
  public ThemeRepositoryFactory providesThemeRepositoryFactory(@Named("apiThemeService") ThemeService apiThemeService,
      @Named("cacheThemeService") ThemeService cacheThemeService, DataCacheStorage themeCacheStorage) {
    return new ThemeRepositoryFactory(apiThemeService, cacheThemeService, themeCacheStorage);
  }

  @Provides
  @Singleton
  public ThemeRepository providesThemeRepositoryImp(ThemeRepositoryFactory themeRepositoryFactory) {
    return new ThemeRepositoryImp(themeRepositoryFactory);
  }
}
