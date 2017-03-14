package com.freelance.themes.presentation.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.freelance.themes.R;
import com.freelance.themes.presentation.ThemesApp;
import com.freelance.themes.presentation.di.component.ThemesAppComponent;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

  protected Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    super.onCreate(savedInstanceState);
    setContentView(getLayout());
    injectDependencies();
    ButterKnife.bind(this);
  }

  protected abstract int getLayout();

  protected void setupToolbar(String title, boolean displayHome) {
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(displayHome);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
      getSupportActionBar().setTitle(title);
    }
  }

  private void injectDependencies() {
    setUpComponent(ThemesApp.getApp(this).getComponent());
  }

  public abstract void setUpComponent(ThemesAppComponent appComponent);

}
