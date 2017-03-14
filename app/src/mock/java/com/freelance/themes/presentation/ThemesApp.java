package com.freelance.themes.presentation;

import android.app.Application;
import android.content.Context;

import com.freelance.themes.di.module.ApiModule;
import com.freelance.themes.di.module.CacheModule;
import com.freelance.themes.presentation.di.component.DaggerThemesAppComponent;
import com.freelance.themes.presentation.di.component.ThemesAppComponent;
import com.freelance.themes.presentation.di.module.ThemesAppModule;

public class ThemesApp extends Application {

  private ThemesAppComponent component;

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
  }

  public void setupGraph(String apiType, String cacheType) {
    component = DaggerThemesAppComponent.builder()
        .themesAppModule(new ThemesAppModule(this))
        .cacheModule(new CacheModule(cacheType))
        .apiModule(new ApiModule(apiType))
        .build();
  }

  public ThemesAppComponent getComponent() {
    return component;
  }

  public static ThemesApp getApp(Context context) {
    return (ThemesApp) context.getApplicationContext();
  }
}
