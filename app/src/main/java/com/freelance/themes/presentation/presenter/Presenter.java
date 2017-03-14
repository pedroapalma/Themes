package com.freelance.themes.presentation.presenter;

import com.freelance.themes.presentation.contract.BaseView;

public interface Presenter<V extends BaseView> {

  void attachView(V view);

  void detachView();
}