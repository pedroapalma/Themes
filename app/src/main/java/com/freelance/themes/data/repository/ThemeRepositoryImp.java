package com.freelance.themes.data.repository;

import com.freelance.themes.data.entity.ThemeEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class ThemeRepositoryImp implements ThemeRepository {

  private ThemeRepositoryFactory themeRepositoryFactory;

  @Inject
  public ThemeRepositoryImp(ThemeRepositoryFactory themeRepositoryFactory) {
    this.themeRepositoryFactory = themeRepositoryFactory;
  }

  @Override
  public Observable<List<ThemeEntity>> themes() {
    final ThemeService themeService = themeRepositoryFactory.create();
    return themeService.getThemeList();
  }
}
