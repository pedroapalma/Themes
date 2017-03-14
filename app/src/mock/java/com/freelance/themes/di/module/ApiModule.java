package com.freelance.themes.di.module;

import com.freelance.themes.data.api.RedditApiService;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

@Module
public class ApiModule {

  public static final String IO_EXCEPTION = "io_exception";
  public static final String HTTP_EXCEPTION = "http_exception";
  public static final String UNEXPECTED_EXCEPTION = "unexpected_exception";

  private String type;

  public ApiModule(String type) {
    this.type = type;
  }

  @Provides
  @Singleton
  public RedditApiService providesRedditApiService() {
    MockRedditApiService mockRedditApiService = new MockRedditApiService();
    if (type.equals(IO_EXCEPTION))
      mockRedditApiService.setDummyRedditThemesResult(Observable.error(new IOException()));
    else if (type.equals(HTTP_EXCEPTION))
      mockRedditApiService.setDummyRedditThemesResult(get403ForbiddenError());
    else if (type.equals(UNEXPECTED_EXCEPTION))
      mockRedditApiService.setDummyRedditThemesResult(Observable.error(new Exception()));
    return mockRedditApiService;
  }

  private Observable get403ForbiddenError() {
    return Observable.error(new HttpException(
        Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));
  }
}
