package com.freelance.themes.data.repository.datasource;

import com.freelance.themes.data.api.RedditApiService;
import com.freelance.themes.data.api.mapper.ApiMapper;
import com.freelance.themes.data.cache.DataCacheStorage;
import com.freelance.themes.data.entity.ThemeEntity;
import com.freelance.themes.data.repository.ThemeService;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

public class ApiThemeService implements ThemeService {

  private RedditApiService redditApiService;
  private ApiMapper apiDataMapper;
  private DataCacheStorage themesCacheStorage;

  public ApiThemeService(RedditApiService redditApiService, ApiMapper apiDataMapper, DataCacheStorage themesCacheStorage) {
    this.redditApiService = redditApiService;
    this.apiDataMapper = apiDataMapper;
    this.themesCacheStorage = themesCacheStorage;
  }

  @Override
  public Observable<List<ThemeEntity>> getThemeList() {
    return redditApiService.getThemeData()
        .map(apiDataMapper::convert)
        .doOnNext(saveThemeListToCacheStore);
  }

  @SuppressWarnings("unchecked")
  private final Action1<List<ThemeEntity>> saveThemeListToCacheStore = themeListEntity -> {
    if (themeListEntity != null) {
      themesCacheStorage.save(themeListEntity);
    }
  };
}
