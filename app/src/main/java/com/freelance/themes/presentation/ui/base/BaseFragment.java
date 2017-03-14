package com.freelance.themes.presentation.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelance.themes.presentation.ThemesApp;
import com.freelance.themes.presentation.di.component.ThemesAppComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

  private Unbinder unbinder;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View fragmentView = inflater.inflate(getFragmentLayout(), container, false);
    injectDependencies();
    bindViews(fragmentView);
    return fragmentView;
  }

  protected abstract int getFragmentLayout();

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbindViews();
  }

  private void bindViews(View rootView) {
    unbinder = ButterKnife.bind(this, rootView);
  }

  private void unbindViews() {
    unbinder.unbind();
  }

  private void injectDependencies() {
    setUpComponent(ThemesApp.getApp(getActivity()).getComponent());
  }

  protected abstract void setUpComponent(ThemesAppComponent component);
}
