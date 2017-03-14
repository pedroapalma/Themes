package com.freelance.themes.presentation.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.freelance.themes.R;
import com.freelance.themes.presentation.model.ThemeModel;
import com.freelance.themes.presentation.ui.activity.MainActivity;
import com.freelance.themes.presentation.ui.activity.ThemeDetailsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ThemeDetailsFragmentTest {

  @Rule
  public ActivityTestRule<MainActivity> mActivityRuleOne = new ActivityTestRule<MainActivity>(
      MainActivity.class, false, false) {
  };

  @Rule
  public ActivityTestRule<ThemeDetailsActivity> mActivityRuleTwo = new ActivityTestRule<ThemeDetailsActivity>(
      ThemeDetailsActivity.class, false, false) {
  };

  @Test
  public void test_successCase_showThemeDetailsFromMainActivity() {

    mActivityRuleOne.launchActivity(new Intent());

    onView(ViewMatchers.withId(R.id.recyclerView_themes))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    onView(withId(R.id.text_advertiser_category))
        .check(matches(withText("Games")))
        .check(matches(isDisplayed()));

    onView(withText("Overwatch"))
        .check(matches(withParent(withId(R.id.toolbar))));

    onView(withId(R.id.text_subreddit_type))
        .check(matches(withText("public")))
        .check(matches(isDisplayed()));

    onView(withId(R.id.text_subscribers))
        .check(matches(withText("10000")))
        .check(matches(isDisplayed()));

    onView(withId(R.id.label_subscribers))
        .check(matches(withText("subscribers")))
        .check(matches(isDisplayed()));
  }

  @Test
  public void test_successCase_showThemeDetails() {

    mActivityRuleTwo.launchActivity(createIntent());

    onView(withId(R.id.text_advertiser_category))
        .check(matches(withText("Games")))
        .check(matches(isDisplayed()));

    onView(withText("Overwatch"))
        .check(matches(withParent(withId(R.id.toolbar))));

    onView(withId(R.id.text_subreddit_type))
        .check(matches(withText("public")))
        .check(matches(isDisplayed()));

    onView(withId(R.id.text_subscribers))
        .check(matches(withText("10000")))
        .check(matches(isDisplayed()));

    onView(withId(R.id.label_subscribers))
        .check(matches(withText("subscribers")))
        .check(matches(isDisplayed()));
  }

  private Intent createIntent() {
    Context appContext = InstrumentationRegistry.getTargetContext();
    Intent intent = new Intent(appContext, ThemeDetailsActivity.class);
    intent.putExtra(ThemesFragment.EXTRA_THEME, createDummyThemeModel());
    return intent;
  }

  private ThemeModel createDummyThemeModel() {
    ThemeModel themeModel = new ThemeModel();
    themeModel.setBannerImg("http://www.game-legends.com/media/image/GL_Banner_Overwatch1.jpg");
    themeModel.setIconImg("http://orig09.deviantart.net/b520/f/2016/136/f/3/overwatch_tracer_icon_by_troublem4ker-d9qw8vz.png");
    themeModel.setHeaderImg("http://orig09.deviantart.net/b520/f/2016/136/f/3/overwatch_tracer_icon_by_troublem4ker-d9qw8vz.png");
    themeModel.setAdvertiserCategory("Games");
    themeModel.setTitle("Overwatch(Test)");
    themeModel.setSubredditType("public");
    themeModel.setPublicDescription("Description for testing.");
    themeModel.setSubscribers(10000);
    themeModel.setDescriptionHtml("Large description for testing.");
    themeModel.setUrl("/r/Overwatch");
    themeModel.setDisplayName("Overwatch");
    return themeModel;
  }
}