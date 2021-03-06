package com.freelance.themes.data.repository;

import com.freelance.themes.data.entity.ThemeEntity;

import java.util.List;

import rx.Observable;

public interface ThemeRepository {
  Observable<List<ThemeEntity>> themes();
}
