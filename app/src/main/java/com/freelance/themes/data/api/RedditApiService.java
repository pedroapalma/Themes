package com.freelance.themes.data.api;

import com.google.gson.JsonObject;

import retrofit2.http.GET;
import rx.Observable;

public interface RedditApiService {

  @GET("reddits.json")
  Observable<JsonObject> getThemeData();
}
