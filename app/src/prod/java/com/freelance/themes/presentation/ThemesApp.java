package com.freelance.themes.presentation;

import android.app.Application;
import android.content.Context;

import com.freelance.themes.presentation.di.component.DaggerThemesAppComponent;
import com.freelance.themes.presentation.di.component.ThemesAppComponent;
import com.freelance.themes.presentation.di.module.ThemesAppModule;

public class ThemesApp extends Application {

  private ThemesAppComponent component;

  @Override
  public void onCreate() {
    super.onCreate();
    setupGraph();
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
  }

  private void setupGraph() {
    component = DaggerThemesAppComponent.builder()
        .themesAppModule(new ThemesAppModule(this))
        .build();
  }

  public ThemesAppComponent getComponent() {
    return component;
  }

  public static ThemesApp getApp(Context context) {
    return (ThemesApp) context.getApplicationContext();
  }
}
