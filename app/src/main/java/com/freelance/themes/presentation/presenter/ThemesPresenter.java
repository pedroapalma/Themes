package com.freelance.themes.presentation.presenter;

import com.freelance.themes.data.entity.ThemeEntity;
import com.freelance.themes.data.repository.ThemeRepository;
import com.freelance.themes.presentation.ErrorMessageFactory;
import com.freelance.themes.presentation.contract.ThemesContract;
import com.freelance.themes.presentation.model.ThemeModel;
import com.freelance.themes.presentation.model.mapper.ThemeModelMapper;

import java.util.List;

import rx.Scheduler;

public class ThemesPresenter extends BasePresenter<ThemesContract.View> implements ThemesContract.UserActionsListener {

  private final Scheduler mainScheduler, ioScheduler;
  private ThemeRepository themeRepository;
  private ThemeModelMapper themeModelMapper;

  public ThemesPresenter(ThemeRepository themeRepository, Scheduler ioScheduler, Scheduler mainScheduler,
      ThemeModelMapper themeModelMapper) {
    this.themeRepository = themeRepository;
    this.ioScheduler = ioScheduler;
    this.mainScheduler = mainScheduler;
    this.themeModelMapper = themeModelMapper;
  }

  @Override
  public void loadThemes() {
    getView().hideError();
    getView().setProgressIndicator(true);
    addSubscription(themeRepository.themes().subscribeOn(ioScheduler).observeOn(mainScheduler)
        .subscribe(new ThemesSubscriber()));
  }

  @Override
  public void openThemeDetail(ThemeModel themeModel) {
    getView().showThemeDetail(themeModel);
  }

  @Override
  public void openUrl(String url) {
    getView().showUrl(url);
  }

  private class ThemesSubscriber extends DefaultSubscriber<List<ThemeEntity>> {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
      getView().setProgressIndicator(false);
      String errorMessage = ErrorMessageFactory.create(getContext(), e);
      getView().showError(errorMessage);
    }

    @Override
    public void onNext(List<ThemeEntity> themeEntityList) {
      getView().setProgressIndicator(false);
      getView().showThemes(themeModelMapper.convert(themeEntityList));
    }
  }
}
