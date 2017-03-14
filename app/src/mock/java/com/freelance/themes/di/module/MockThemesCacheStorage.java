package com.freelance.themes.di.module;

import com.freelance.themes.data.cache.DataCacheStorage;
import com.freelance.themes.data.entity.ThemeEntity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class MockThemesCacheStorage implements DataCacheStorage<List<ThemeEntity>> {

  private Observable dummyCacheThemesResult = null;
  private boolean exists = false;

  public void setDummyCacheThemesResult(Observable result) {
    dummyCacheThemesResult = result;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Observable<List<ThemeEntity>> getObservable() {
    if (dummyCacheThemesResult != null) {
      return dummyCacheThemesResult;
    }
    return Observable.just(createMockThemeEntityList());
  }

  @Override
  public List<ThemeEntity> get() {
    return createMockThemeEntityList();
  }

  @Override
  public void save(List<ThemeEntity> data) {

  }

  @Override
  public boolean clear() {
    return true;
  }

  @Override
  public boolean exists() {
    return exists;
  }

  private List<ThemeEntity> createMockThemeEntityList() {
    List<ThemeEntity> themeEntityList = new ArrayList<>();
    ThemeEntity themeEntity = new ThemeEntity();
    themeEntity.setBannerImg("http://www.game-legends.com/media/image/GL_Banner_Overwatch1.jpg");
    themeEntity.setIconImg("http://orig09.deviantart.net/b520/f/2016/136/f/3/overwatch_tracer_icon_by_troublem4ker-d9qw8vz.png");
    themeEntity.setHeaderImg("http://orig09.deviantart.net/b520/f/2016/136/f/3/overwatch_tracer_icon_by_troublem4ker-d9qw8vz.png");
    themeEntity.setAdvertiserCategory("Games");
    themeEntity.setTitle("Overwatch(Test)");
    themeEntity.setSubredditType("public");
    themeEntity.setPublicDescription("Description for testing.");
    themeEntity.setSubscribers(10000);
    themeEntity.setDescriptionHtml("Large description for testing.");
    themeEntity.setUrl("/r/Overwatch");
    themeEntity.setDisplayName("Overwatch");
    themeEntityList.add(themeEntity);
    return themeEntityList;
  }

  public void setExists(boolean exists) {
    this.exists = exists;
  }
}
