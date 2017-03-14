package com.freelance.themes.presentation.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelance.themes.R;
import com.freelance.themes.presentation.model.ThemeModel;
import com.freelance.themes.presentation.ui.base.BaseAdapter;
import com.freelance.themes.presentation.ui.base.BaseViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ThemesAdapter extends BaseAdapter<ThemeModel, ThemesAdapter.ThemesViewHolder> {

  private static final String HTTPS_WWW_REDDIT_COM = "https://www.reddit.com";
  private Context context;
  private ItemViewListener listener;
  private int lastPosition = -1;


  public ThemesAdapter(Context context, ItemViewListener listener) {
    this.context = context;
    this.listener = listener;
  }

  @Override
  public ThemesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ThemesViewHolder(getItemViewInflated(R.layout.item_row_themes, parent));
  }

  @Override
  public void onBindViewHolder(ThemesViewHolder holder, int position) {
    ThemeModel themeModel = getItem(position);
    holder.titleText.setText(themeModel.getTitle());
    String subredditType = themeModel.getSubredditType().substring(0, 1).toUpperCase() + themeModel.getSubredditType().substring(1);
    holder.subredditTypeText.setText(subredditType);
    holder.advertiserCategoryText.setText(themeModel.getAdvertiserCategory());
    holder.publicDescriptionText.setText(themeModel.getPublicDescription());
    String url = HTTPS_WWW_REDDIT_COM + themeModel.getUrl();
    holder.urlText.setText(url);
    holder.urlText.setPaintFlags(holder.urlText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    if (TextUtils.isEmpty(themeModel.getBannerImg()))
      holder.bannerImage.setVisibility(View.GONE);
    else {
      holder.bannerImage.setVisibility(View.VISIBLE);
      holder.setImageView(themeModel.getBannerImg(), holder.bannerImage, R.drawable.banner_image_place_holder);
    }

    if (themeModel.getIconImg() == null || TextUtils.isEmpty(themeModel.getIconImg()))
      holder.setImageView(themeModel.getHeaderImg(), holder.headerImage, R.drawable.header_image_place_holder);
    else
      holder.setImageView(themeModel.getIconImg(), holder.headerImage, R.drawable.header_image_place_holder);

    Animation animation = AnimationUtils.loadAnimation(context,
        (position > lastPosition) ? R.anim.up_from_bottom
            : R.anim.down_from_top);
    holder.itemView.startAnimation(animation);
    lastPosition = position;

  }

  @Override
  public void onViewDetachedFromWindow(ThemesViewHolder holder) {
    super.onViewDetachedFromWindow(holder);
    holder.itemView.clearAnimation();
  }

  class ThemesViewHolder extends BaseViewHolder {

    @BindView(R.id.image_banner)
    ImageView bannerImage;

    @BindView(R.id.image_header)
    CircleImageView headerImage;

    @BindView(R.id.text_title)
    TextView titleText;

    @BindView(R.id.text_subreddit_type)
    TextView subredditTypeText;

    @BindView(R.id.text_advertiser_category)
    TextView advertiserCategoryText;

    @BindView(R.id.text_public_description)
    TextView publicDescriptionText;

    @BindView(R.id.text_url)
    TextView urlText;

    ThemesViewHolder(View itemView) {
      super(itemView);
      urlText.setOnClickListener(view -> listener.onClickUrl(HTTPS_WWW_REDDIT_COM + getItem(getAdapterPosition()).getUrl()));
    }

    @OnClick(R.id.layout_theme_item_row)
    void onClickThemeItemRowLayout() {
      listener.onClickItemView(getItem(getAdapterPosition()));
    }

    @Override
    protected void onClickItemView(View view) {
    }

    void setImageView(String urlImage, ImageView image, @DrawableRes int placeHolderId) {
      Picasso.with(context)
          .load(TextUtils.isEmpty(urlImage) ? null : urlImage)
          .placeholder(placeHolderId)
          .into(image, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
            }
          });
    }
  }

  public interface ItemViewListener {
    void onClickItemView(ThemeModel themeModel);

    void onClickUrl(String url);
  }
}
