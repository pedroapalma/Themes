package com.freelance.themes.presentation.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.freelance.themes.R;
import com.freelance.themes.data.cache.DataCacheStorage;
import com.freelance.themes.presentation.di.component.ThemesAppComponent;
import com.freelance.themes.presentation.ui.base.BaseActivity;
import com.freelance.themes.presentation.ui.fragment.ThemesFragment;

import javax.inject.Inject;


public class MainActivity extends BaseActivity {

  @Inject
  DataCacheStorage themesCacheStorage;

  @Override
  protected int getLayout() {
    return R.layout.activity_main;
  }

  @Override
  public void setUpComponent(ThemesAppComponent appComponent) {
    appComponent.inject(this);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupToolbar(getString(R.string.app_name), false);

    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.fragment_container);

    if (fragment == null) {
      fragment = new ThemesFragment();
      fm.beginTransaction()
          .add(R.id.fragment_container, fragment)
          .commit();
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_clear_cache:
        themesCacheStorage.clear();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

}
