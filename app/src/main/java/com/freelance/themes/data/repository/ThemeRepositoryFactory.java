package com.freelance.themes.data.repository;

import com.freelance.themes.data.cache.DataCacheStorage;
import com.freelance.themes.presentation.di.scope.Named;

import javax.inject.Inject;

public class ThemeRepositoryFactory {

  private ThemeService apiThemeService;
  private ThemeService cacheThemeService;
  private DataCacheStorage themesCacheStorage;

  @Inject
  public ThemeRepositoryFactory(@Named("apiThemeService") ThemeService apiThemeService,
      @Named("cacheThemeService") ThemeService cacheThemeService, DataCacheStorage themesCacheStorage) {
    this.apiThemeService = apiThemeService;
    this.cacheThemeService = cacheThemeService;
    this.themesCacheStorage = themesCacheStorage;
  }

  public ThemeService create() {
    if (themesCacheStorage.exists())
      return cacheThemeService;
    else
      return apiThemeService;
  }
}
