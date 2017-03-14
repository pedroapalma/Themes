package com.freelance.themes.presentation.di.module;

import com.freelance.themes.data.repository.ThemeRepositoryImp;
import com.freelance.themes.presentation.contract.BaseView;
import com.freelance.themes.presentation.contract.ThemesContract;
import com.freelance.themes.presentation.di.scope.ActivityScope;
import com.freelance.themes.presentation.model.mapper.ThemeModelMapper;
import com.freelance.themes.presentation.presenter.ThemesPresenter;
import com.freelance.themes.presentation.ui.base.BaseFragment;

import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class ThemesModule {

  private BaseView view;

  public ThemesModule(BaseView view) {
    this.view = view;
  }

  @Provides
  @ActivityScope
  public ThemeModelMapper providesThemeModelMapper() {
    return new ThemeModelMapper();
  }

  @Provides
  @ActivityScope
  public ThemesPresenter providesThemesPresenter(ThemeRepositoryImp themeRepository, ThemeModelMapper themeModelMapper) {
    ThemesPresenter themesPresenter = new ThemesPresenter(themeRepository, Schedulers.io(), AndroidSchedulers.mainThread(), themeModelMapper);
    themesPresenter.attachView((ThemesContract.View) view);
    themesPresenter.attachContext(((BaseFragment) view).getActivity());
    return  themesPresenter;
  }
}
