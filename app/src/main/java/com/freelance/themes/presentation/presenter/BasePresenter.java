package com.freelance.themes.presentation.presenter;

import android.content.Context;

import com.freelance.themes.presentation.contract.BaseView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T extends BaseView> implements Presenter<T> {

  private T view;
  private Context context;

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Override
  public void attachView(T view) {
    this.view = view;
  }

  @Override
  public void detachView() {
    compositeSubscription.clear();
    view = null;
  }

  public T getView() {
    return view;
  }

  private boolean isViewAttached() {
    return view != null;
  }

  protected void addSubscription(Subscription subscription) {
    this.compositeSubscription.add(subscription);
  }

  public void attachContext(Context context) {
    this.context = context;
  }

  public Context getContext() {
    return context;
  }
}
