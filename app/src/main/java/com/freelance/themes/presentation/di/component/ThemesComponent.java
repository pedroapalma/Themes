package com.freelance.themes.presentation.di.component;

import com.freelance.themes.presentation.di.module.ThemesModule;
import com.freelance.themes.presentation.di.scope.ActivityScope;
import com.freelance.themes.presentation.ui.fragment.ThemesFragment;

import dagger.Component;

@ActivityScope
@Component(
    dependencies = ThemesAppComponent.class,
    modules = {
        ThemesModule.class
    }
)
public interface ThemesComponent {
  void inject(ThemesFragment fragment);
}
