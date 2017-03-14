package com.freelance.themes.presentation.di.component;

import com.freelance.themes.data.cache.DataCacheStorage;
import com.freelance.themes.data.repository.ThemeRepositoryImp;
import com.freelance.themes.presentation.ui.activity.MainActivity;
import com.freelance.themes.di.module.ApiModule;
import com.freelance.themes.di.module.CacheModule;
import com.freelance.themes.presentation.di.module.ThemesAppModule;
import com.freelance.themes.presentation.ui.activity.ThemeDetailsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
    modules = {
        ThemesAppModule.class,
        ApiModule.class,
        CacheModule.class
    }
)
public interface ThemesAppComponent {

  void inject(MainActivity activity);

  void inject(ThemeDetailsActivity activity);

  ThemeRepositoryImp getThemeRepositoryImp();

  DataCacheStorage getThemeCacheStorage();
}
