package com.freelance.themes.presentation.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.freelance.themes.R;
import com.freelance.themes.presentation.di.component.ThemesAppComponent;
import com.freelance.themes.presentation.model.ThemeModel;
import com.freelance.themes.presentation.ui.base.BaseFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ThemeDetailsFragment extends BaseFragment {

  ThemeModel themeModel;

  int primaryColor;

  int primaryDarkColor;

  ImageView headerImage;

  ImageView bannerImage;

  @BindView(R.id.text_subreddit_type)
  TextView subredditTypeText;

  @BindView(R.id.text_subscribers)
  TextView subscribersText;

  @BindView(R.id.webView_description)
  WebView descriptionWebView;

  @BindView(R.id.text_advertiser_category)
  TextView advertiserCategoryText;

  @BindView(R.id.bullet_two)
  TextView bulletTwo;

  @Override
  protected int getFragmentLayout() {
    return R.layout.fragment_theme_details;
  }

  @Override
  protected void setUpComponent(ThemesAppComponent component) {

  }

  public static ThemeDetailsFragment newInstance(ThemeModel themeModel) {
    ThemeDetailsFragment fragment = new ThemeDetailsFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(ThemesFragment.EXTRA_THEME, themeModel);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    themeModel = getArguments().getParcelable(ThemesFragment.EXTRA_THEME);
    prepareView();
    setThemeDetailsContent();
  }

  private void prepareView() {
    headerImage = ButterKnife.findById(getActivity(), R.id.image_header);
    String headerImageUrl = getHeaderImageUrl(themeModel);
    setHeaderImageIntoTheView(headerImageUrl);
    bannerImage = ButterKnife.findById(getActivity(), R.id.image_banner);
    setBannerImageIntoTheView(themeModel.getBannerImg());
    setupBaseColors(themeModel);
    setupCollapsingToolbar();
    setStatusBarColor(primaryDarkColor);
  }

  private String getHeaderImageUrl(ThemeModel themeModel) {
    if (themeModel.getIconImg() == null || TextUtils.isEmpty(themeModel.getIconImg()))
      return themeModel.getHeaderImg();
    else
      return themeModel.getIconImg();
  }

  private void setHeaderImageIntoTheView(String urlImage) {
    Picasso.with(getActivity())
        .load(TextUtils.isEmpty(urlImage) ? null : urlImage)
        .placeholder(R.drawable.header_image_place_holder)
        .into(headerImage);
  }

  private void setBannerImageIntoTheView(String urlImage) {
    Picasso.with(getActivity())
        .load(TextUtils.isEmpty(urlImage) ? null : urlImage)
        .into(bannerImage);
  }

  private void setupBaseColors(ThemeModel themeModel) {
    try {
      if (!TextUtils.isEmpty(themeModel.getKeyColor()) && TextUtils.isEmpty(themeModel.getBannerImg())) {
        primaryColor = Color.parseColor(themeModel.getKeyColor());
        primaryDarkColor = shiftColor(primaryColor, 0.7f);
      } else {
        BitmapDrawable drawable;
        if (TextUtils.isEmpty(themeModel.getBannerImg()))
          drawable = (BitmapDrawable) headerImage.getDrawable();
        else
          drawable = (BitmapDrawable) bannerImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Palette palette = Palette.from(bitmap).generate();
        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
        if (vibrantSwatch != null) {
          primaryColor = vibrantSwatch.getRgb();
          primaryDarkColor = shiftColor(vibrantSwatch.getRgb(), 0.7f);
        } else {
          setupDefaultBaseColor();
        }
      }
    } catch (Exception e) {
      setupDefaultBaseColor();
    }
  }

  private int shiftColor(@ColorInt int color, @FloatRange(from = 0.0f, to = 2.0f) float by) {
    if (by == 1f) return color;
    float[] hsv = new float[3];
    Color.colorToHSV(color, hsv);
    hsv[2] *= by;
    return Color.HSVToColor(hsv);
  }

  private void setupDefaultBaseColor() {
    primaryColor = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
    primaryDarkColor = ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark);
  }

  private void setupCollapsingToolbar() {
    CollapsingToolbarLayout collapsingToolbarLayout = ButterKnife.findById(getActivity(), R.id.toolbar_layout);
    collapsingToolbarLayout.setBackgroundColor(primaryColor);
    collapsingToolbarLayout.setContentScrimColor(primaryColor);
  }

  private void setStatusBarColor(int color) {
    if (Build.VERSION.SDK_INT >= 21)
      getActivity().getWindow().setStatusBarColor(color);
  }

  private void setWebViewContent() {
    String description = fromHtml(themeModel.getDescriptionHtml());
    descriptionWebView.loadDataWithBaseURL(null, description, "text/html", "utf-8", null);
    descriptionWebView.setInitialScale(170);
  }

  @SuppressWarnings("deprecation")
  private String fromHtml(String text) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
      return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString();
    } else {
      return Html.fromHtml(text).toString();
    }
  }

  private void setThemeDetailsContent() {
    setWebViewContent();
    subredditTypeText.setText(themeModel.getSubredditType());
    subscribersText.setText(String.valueOf(themeModel.getSubscribers()));
    if (TextUtils.isEmpty(themeModel.getAdvertiserCategory()))
      bulletTwo.setVisibility(View.GONE);
    advertiserCategoryText.setText(themeModel.getAdvertiserCategory());
    RelativeLayout opacityLayout = ButterKnife.findById(getActivity(), R.id.layout_opacity);
    if (TextUtils.isEmpty(themeModel.getBannerImg()))
      opacityLayout.setVisibility(View.GONE);
    else
      opacityLayout.setVisibility(View.VISIBLE);
  }
}
