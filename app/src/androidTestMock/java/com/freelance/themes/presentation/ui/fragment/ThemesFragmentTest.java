package com.freelance.themes.presentation.ui.fragment;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.freelance.themes.di.module.ApiModule;
import com.freelance.themes.di.module.CacheModule;
import com.freelance.themes.presentation.RxIdlingResource;
import com.freelance.themes.presentation.ui.activity.MainActivity;
import com.freelance.themes.presentation.ui.base.BaseActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class ThemesFragmentTest {

  @Rule
  public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(
      MainActivity.class, false, false) {
  };

  private RxIdlingResource rxIdlingResource;

  @Before
  public void setUp() throws Exception {
    rxIdlingResource = RxIdlingResource.getInstance();
    Espresso.registerIdlingResources(rxIdlingResource.getIdlingResource());
    RxIdlingResource.increment();
  }

  @After
  public void tearDown() throws Exception {
    Espresso.unregisterIdlingResources(rxIdlingResource.getIdlingResource());
  }

  @Test
  public void test_successCase_GetThemesFromApiAndShowIntoTheView() {
    Intent intent = new Intent();
    mActivityRule.launchActivity(intent);

    onView(withText("Games")).check(matches(isDisplayed()));
    onView(withText("Overwatch(Test)")).check(matches(isDisplayed()));
    onView(withText("Public")).check(matches(isDisplayed()));
    onView(withText("Description for testing.")).check(matches(isDisplayed()));
    onView(withText("https://www.reddit.com/r/Overwatch")).check(matches(isDisplayed()));
  }

  @Test
  public void test_IOException_GetThemesFromApiAndShowIntoTheView() {
    Intent intent = new Intent();
    intent.putExtra(BaseActivity.EXTRA_API_TYPE, ApiModule.IO_EXCEPTION);
    mActivityRule.launchActivity(intent);

    String errorMsg = "Connection error";
    onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(errorMsg)))
        .check(matches(withEffectiveVisibility(
            ViewMatchers.Visibility.VISIBLE
        )));
  }

  @Test
  public void test_HttpException_GetThemesFromApiAndShowIntoTheView() {
    Intent intent = new Intent();
    intent.putExtra(BaseActivity.EXTRA_API_TYPE, ApiModule.HTTP_EXCEPTION);
    mActivityRule.launchActivity(intent);

    String errorMsg = "Server error";
    onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(errorMsg)))
        .check(matches(withEffectiveVisibility(
            ViewMatchers.Visibility.VISIBLE
        )));
  }

  @Test
  public void test_UnexpectedException_GetThemesFromApiAndShowIntoTheView() {
    Intent intent = new Intent();
    intent.putExtra(BaseActivity.EXTRA_API_TYPE, ApiModule.UNEXPECTED_EXCEPTION);
    mActivityRule.launchActivity(intent);

    String errorMsg = "Unexpected error";
    onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(errorMsg)))
        .check(matches(withEffectiveVisibility(
            ViewMatchers.Visibility.VISIBLE
        )));
  }

  @Test
  public void test_successCase_GetThemesFromCacheAndShowIntoTheView() {
    Intent intent = new Intent();
    intent.putExtra(BaseActivity.EXTRA_CACHE_TYPE, CacheModule.SUCCESS_CASE);
    mActivityRule.launchActivity(intent);

    onView(withText("Games")).check(matches(isDisplayed()));
    onView(withText("Overwatch(Test)")).check(matches(isDisplayed()));
    onView(withText("Public")).check(matches(isDisplayed()));
    onView(withText("Description for testing.")).check(matches(isDisplayed()));
    onView(withText("https://www.reddit.com/r/Overwatch")).check(matches(isDisplayed()));
  }

  @Test
  public void test_UnexpectedException_GetThemesFromCacheAndShowIntoTheView() {
    Intent intent = new Intent();
    intent.putExtra(BaseActivity.EXTRA_CACHE_TYPE, CacheModule.UNEXPECTED_EXCEPTION);
    mActivityRule.launchActivity(intent);

    String errorMsg = "Unexpected error";
    onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(errorMsg)))
        .check(matches(withEffectiveVisibility(
            ViewMatchers.Visibility.VISIBLE
        )));
  }

  @Test
  public void test_NotFound_GetThemesFromCacheAndShowIntoTheView() {
    Intent intent = new Intent();
    intent.putExtra(BaseActivity.EXTRA_CACHE_TYPE, CacheModule.NOT_FOUND_EXCEPTION);
    mActivityRule.launchActivity(intent);

    String errorMsg = "Not found";
    onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(errorMsg)))
        .check(matches(withEffectiveVisibility(
            ViewMatchers.Visibility.VISIBLE
        )));
  }
}