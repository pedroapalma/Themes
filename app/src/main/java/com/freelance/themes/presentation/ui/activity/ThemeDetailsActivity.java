package com.freelance.themes.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.freelance.themes.R;
import com.freelance.themes.presentation.di.component.ThemesAppComponent;
import com.freelance.themes.presentation.model.ThemeModel;
import com.freelance.themes.presentation.ui.base.BaseActivity;
import com.freelance.themes.presentation.ui.fragment.ThemeDetailsFragment;
import com.freelance.themes.presentation.ui.fragment.ThemesFragment;


public class ThemeDetailsActivity extends BaseActivity {
  @Override
  protected int getLayout() {
    return R.layout.activity_theme_details;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    ThemeModel themeModel = intent.getParcelableExtra(ThemesFragment.EXTRA_THEME);

    setupToolbar(themeModel.getDisplayName(), true);

    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.fragment_container);

    if (fragment == null) {
      fragment = ThemeDetailsFragment.newInstance(themeModel);
      fm.beginTransaction()
          .add(R.id.fragment_container, fragment)
          .commit();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        super.onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void setUpComponent(ThemesAppComponent appComponent) {

  }
}
