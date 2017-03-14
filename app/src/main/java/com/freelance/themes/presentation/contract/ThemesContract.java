package com.freelance.themes.presentation.contract;

import com.freelance.themes.presentation.model.ThemeModel;

import java.util.ArrayList;

public class ThemesContract {

  public interface View extends BaseView {
    void showThemes(ArrayList<ThemeModel> themeList);

    void showThemeDetail(ThemeModel theme);

    void showUrl(String url);

    void setProgressIndicator(boolean active);

    void showError(String message);

    void hideError();
  }

  public interface UserActionsListener {
    void loadThemes();

    void openThemeDetail(ThemeModel theme);

    void openUrl(String url);
  }
}
