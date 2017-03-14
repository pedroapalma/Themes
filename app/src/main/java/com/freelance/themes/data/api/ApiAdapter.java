package com.freelance.themes.data.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapter {

  private static Retrofit adapter;

  private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

  public static Retrofit getRetrofitInstance() {

    setupOkHttpClient();

    adapter = new Retrofit.Builder()
        .client(httpClient.build())
        .baseUrl(ApiConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    return adapter;
  }

  private static void setupOkHttpClient() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    httpClient.addInterceptor(interceptor);
  }
}
