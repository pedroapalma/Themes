package com.freelance.themes.presentation.ui.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.freelance.themes.R;
import com.freelance.themes.presentation.RxIdlingResource;
import com.freelance.themes.presentation.di.component.DaggerThemesComponent;
import com.freelance.themes.presentation.di.component.ThemesAppComponent;
import com.freelance.themes.presentation.di.module.ThemesModule;
import com.freelance.themes.presentation.model.ThemeModel;
import com.freelance.themes.presentation.presenter.ThemesPresenter;
import com.freelance.themes.presentation.ui.base.BaseFragment;
import com.freelance.themes.presentation.contract.ThemesContract;
import com.freelance.themes.presentation.ui.activity.ThemeDetailsActivity;
import com.freelance.themes.presentation.ui.adapter.ThemesAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ThemesFragment extends BaseFragment implements ThemesContract.View {

  public static final String BUNDLE_THEMES = "bundle_themes";
  public static final String EXTRA_THEME = "extra_themes";
  private static final String BUNDLE_SNACKBAR_ERROR_MESSAGE = "bundle_snackbar_error_message";
  private static final String BUNDLE_SNACKBAR_VISIBLE_STATUS = "bundle_snackbar_visible_status";

  @Inject
  ThemesPresenter themesPresenter;

  ArrayList<ThemeModel> themeList;

  @BindView(R.id.recyclerView_themes)
  RecyclerView themesRecyclerView;

  @BindView(R.id.swipe_refresh_layout)
  SwipeRefreshLayout swipeRefreshLayout;

  @BindView(R.id.layout_reddit_themes_empty_state)
  LinearLayout redditThemesEmptyStateLayout;

  ThemesAdapter themesAdapter;

  Snackbar snackbar;

  String snackbarErrorMessage;

  boolean snackbarVisibleStatus;

  @Override
  protected int getFragmentLayout() {
    return R.layout.fragment_themes;
  }

  @Override
  protected void setUpComponent(ThemesAppComponent component) {
    DaggerThemesComponent.builder()
        .themesAppComponent(component)
        .themesModule(new ThemesModule(this))
        .build()
        .inject(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    setupRecyclerView();
    setupSwipeRefreshLayout();

    if (savedInstanceState == null)
      themesPresenter.loadThemes();
    else {
      restoreSavedInstanceState(savedInstanceState);
      displaySnackbarIfWasShowsBefore();
      if (themeList != null)
        themesAdapter.setItems(themeList);
      else {
        themesRecyclerView.setVisibility(View.GONE);
        redditThemesEmptyStateLayout.setVisibility(View.VISIBLE);
      }
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelableArrayList(BUNDLE_THEMES, themeList);
    outState.putString(BUNDLE_SNACKBAR_ERROR_MESSAGE, snackbarErrorMessage);
    outState.putBoolean(BUNDLE_SNACKBAR_VISIBLE_STATUS, snackbar != null && snackbar.isShown());
  }

  private void restoreSavedInstanceState(Bundle savedInstanceState) {
    themeList = savedInstanceState.getParcelableArrayList(BUNDLE_THEMES);
    snackbarErrorMessage = savedInstanceState.getString(BUNDLE_SNACKBAR_ERROR_MESSAGE);
    snackbarVisibleStatus = savedInstanceState.getBoolean(BUNDLE_SNACKBAR_VISIBLE_STATUS);
  }

  private void setupRecyclerView() {
    themesAdapter = new ThemesAdapter(getActivity(), listener);
    themesRecyclerView.setAdapter(themesAdapter);
    themesRecyclerView.setHasFixedSize(true);
    int orientation = this.getResources().getConfiguration().orientation;
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
      themesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    } else {
      themesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }
  }

  private void setupSwipeRefreshLayout() {
    if (getView() == null)
      return;
    SwipeRefreshLayout swipeRefreshLayout = ButterKnife.findById(getView(), R.id.swipe_refresh_layout);
    swipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipe_refresh_colors));
    swipeRefreshLayout.setOnRefreshListener(() -> themesPresenter.loadThemes());
  }

  ThemesAdapter.ItemViewListener listener = new ThemesAdapter.ItemViewListener() {
    @Override
    public void onClickItemView(ThemeModel themeModel) {
      themesPresenter.openThemeDetail(themeModel);
    }

    @Override
    public void onClickUrl(String url) {
      themesPresenter.openUrl(url);
    }
  };

  private void displaySnackbarIfWasShowsBefore() {
    if (snackbarVisibleStatus)
      showError(snackbarErrorMessage);
  }

  @Override
  public void showThemes(ArrayList<ThemeModel> themeList) {
    if (themeList != null) {
      themesAdapter.setItems(themeList);
      themesRecyclerView.setVisibility(View.VISIBLE);
      redditThemesEmptyStateLayout.setVisibility(View.GONE);
    } else {
      themesRecyclerView.setVisibility(View.GONE);
      redditThemesEmptyStateLayout.setVisibility(View.VISIBLE);
    }
    this.themeList = themeList;
    snackbarVisibleStatus = false;
    snackbarErrorMessage = "";
    RxIdlingResource.decrement();
  }

  @Override
  public void showThemeDetail(ThemeModel theme) {
    Intent intent = new Intent(getActivity(), ThemeDetailsActivity.class);
    intent.putExtra(EXTRA_THEME, theme);
    startActivity(intent);
  }

  @Override
  public void showUrl(String url) {
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    startActivity(browserIntent);
  }

  @Override
  public void setProgressIndicator(final boolean active) {
    if (getView() == null)
      return;

    swipeRefreshLayout.post(() -> {
      if (swipeRefreshLayout != null)
        swipeRefreshLayout.setRefreshing(active);
    });
  }

  @Override
  public void showError(String message) {
    if (getView() == null)
      return;

    snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_INDEFINITE)
        .setAction(getString(R.string.retry_uc),
            view -> themesPresenter.loadThemes());
    snackbar.show();
    RxIdlingResource.decrement();
    if (themeList == null) {
      themesRecyclerView.setVisibility(View.GONE);
      redditThemesEmptyStateLayout.setVisibility(View.VISIBLE);
    }
    snackbarErrorMessage = message;
    snackbarVisibleStatus = true;
  }

  @Override
  public void hideError() {
    if (snackbar != null)
      snackbar.dismiss();
  }

}
