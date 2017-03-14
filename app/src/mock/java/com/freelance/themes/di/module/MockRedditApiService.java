package com.freelance.themes.di.module;

import com.freelance.themes.data.api.RedditApiService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import rx.Observable;


public class MockRedditApiService implements RedditApiService {

  private Observable dummyRedditThemesResult = null;

  public void setDummyRedditThemesResult(Observable result) {
    dummyRedditThemesResult = result;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Observable<JsonObject> getThemeData() {
    if (dummyRedditThemesResult != null) {
      return dummyRedditThemesResult;
    }
    return Observable.just(createMockApiData());
  }

  private JsonObject createMockApiData() {
    JsonObject fakeApiDAta = new JsonObject();

    JsonObject jsonTheme = new JsonObject();
    jsonTheme.addProperty("banner_img", "http://www.game-legends.com/media/image/GL_Banner_Overwatch1.jpg");
    jsonTheme.addProperty("icon_img", "http://orig09.deviantart.net/b520/f/2016/136/f/3/overwatch_tracer_icon_by_troublem4ker-d9qw8vz.png");
    jsonTheme.addProperty("header_img", "http://orig09.deviantart.net/b520/f/2016/136/f/3/overwatch_tracer_icon_by_troublem4ker-d9qw8vz.png");
    jsonTheme.addProperty("advertiser_category", "Games");
    jsonTheme.addProperty("title", "Overwatch(Test)");
    jsonTheme.addProperty("subreddit_type", "public");
    jsonTheme.addProperty("public_description", "Description for testing.");
    jsonTheme.addProperty("url", "/r/Overwatch");
    jsonTheme.addProperty("subscribers", 10000);
    jsonTheme.addProperty("description_html", "Large description for testing.");
    jsonTheme.addProperty("display_name", "Overwatch");

    JsonObject jsonWrapperTheme = new JsonObject();
    jsonWrapperTheme.add("data", jsonTheme);
    jsonWrapperTheme.addProperty("kind", "t5");

    JsonArray jsonArrayTheme = new JsonArray();
    jsonArrayTheme.add(jsonWrapperTheme);

    JsonObject jsonData = new JsonObject();
    jsonData.addProperty("modhash", "");
    jsonData.add("children", jsonArrayTheme);
    jsonData.addProperty("after", "t5_2qh61");
    jsonData.add("before", null);

    fakeApiDAta.addProperty("kind", "Listing");
    fakeApiDAta.add("data", jsonData);

    return  fakeApiDAta;
  }
}
