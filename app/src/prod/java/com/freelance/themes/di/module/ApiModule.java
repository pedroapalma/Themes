package com.freelance.themes.di.module;

import com.freelance.themes.data.api.ApiAdapter;
import com.freelance.themes.data.api.RedditApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {

  @Provides
  @Singleton
  public Retrofit providesApiAdapter() {
    return ApiAdapter.getRetrofitInstance();
  }

  @Provides
  @Singleton
  public RedditApiService providesRedditApiService(Retrofit apiAdapter) {
    return apiAdapter.create(RedditApiService.class);
  }
}
