package com.freelance.themes.presentation.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.freelance.themes.R;
import com.freelance.themes.presentation.ThemesApp;
import com.freelance.themes.presentation.di.component.ThemesAppComponent;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {


  public static final String EXTRA_API_TYPE = "extra_api_type";
  public static final String EXTRA_CACHE_TYPE = "extra_cache_type";
  protected Toolbar toolbar;

  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    super.onCreate(savedInstanceState);
    setContentView(getLayout());
    setupTestModulesDependencies();
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

  private void setupTestModulesDependencies() {
    Intent intent = getIntent();
    String apiType = "";
    String cacheType = "";
    if (intent.hasExtra(EXTRA_API_TYPE)) {
      apiType = intent.getStringExtra(EXTRA_API_TYPE);
    }
    if (intent.hasExtra(EXTRA_CACHE_TYPE)) {
      cacheType = intent.getStringExtra(EXTRA_CACHE_TYPE);
    }
    ThemesApp.getApp(this).setupGraph(apiType, cacheType);
  }

}
