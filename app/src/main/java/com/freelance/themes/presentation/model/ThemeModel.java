package com.freelance.themes.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ThemeModel implements Parcelable {

  private String bannerImg;

  private Boolean userSrThemeEnabled;

  private String submitTextHtml;

  private Object userIsBanned;

  private Boolean wikiEnabled;

  private Boolean showMedia;

  private String id;

  private String submitText;

  private String displayName;

  private String headerImg;

  private String descriptionHtml;

  private String title;

  private Boolean collapseDeletedComments;

  private Boolean over18;

  private String publicDescriptionHtml;

  private Boolean spoilersEnabled;

  private List<Integer> iconSize = null;

  private Object suggestedCommentSort;

  private String iconImg;

  private String headerTitle;

  private String description;

  private Object userIsMuted;

  private Object submitLinkLabel;

  private Object accountsActive;

  private Boolean publicTraffic;

  private List<Integer> headerSize = null;

  private Integer subscribers;

  private String submitTextLabel;

  private String whitelistStatus;

  private String lang;

  private Object userIsModerator;

  private String keyColor;

  private String name;

  private Integer created;

  private String url;

  private Boolean quarantine;

  private Boolean hideAds;

  private Integer createdUtc;

  private List<Integer> bannerSize = null;

  private Object userIsContributor;

  private Object accountsActiveIsFuzzed;

  private String advertiserCategory;

  private String publicDescription;

  private Boolean showMediaPreview;

  private Integer commentScoreHideMins;

  private String subredditType;

  private String submissionType;

  private Object userIsSubscriber;

  public ThemeModel() {
  }

  protected ThemeModel(Parcel in) {
    bannerImg = in.readString();
    submitTextHtml = in.readString();
    id = in.readString();
    submitText = in.readString();
    displayName = in.readString();
    headerImg = in.readString();
    descriptionHtml = in.readString();
    title = in.readString();
    publicDescriptionHtml = in.readString();
    iconImg = in.readString();
    headerTitle = in.readString();
    description = in.readString();
    submitTextLabel = in.readString();
    whitelistStatus = in.readString();
    lang = in.readString();
    keyColor = in.readString();
    name = in.readString();
    url = in.readString();
    advertiserCategory = in.readString();
    publicDescription = in.readString();
    subredditType = in.readString();
    submissionType = in.readString();
    subscribers = in.readInt();
  }

  public static final Creator<ThemeModel> CREATOR = new Creator<ThemeModel>() {
    @Override
    public ThemeModel createFromParcel(Parcel in) {
      return new ThemeModel(in);
    }

    @Override
    public ThemeModel[] newArray(int size) {
      return new ThemeModel[size];
    }
  };

  public String getBannerImg() {
    return bannerImg;
  }

  public void setBannerImg(String bannerImg) {
    this.bannerImg = bannerImg;
  }

  public Boolean getUserSrThemeEnabled() {
    return userSrThemeEnabled;
  }

  public void setUserSrThemeEnabled(Boolean userSrThemeEnabled) {
    this.userSrThemeEnabled = userSrThemeEnabled;
  }

  public String getSubmitTextHtml() {
    return submitTextHtml;
  }

  public void setSubmitTextHtml(String submitTextHtml) {
    this.submitTextHtml = submitTextHtml;
  }

  public Object getUserIsBanned() {
    return userIsBanned;
  }

  public void setUserIsBanned(Object userIsBanned) {
    this.userIsBanned = userIsBanned;
  }

  public Boolean getWikiEnabled() {
    return wikiEnabled;
  }

  public void setWikiEnabled(Boolean wikiEnabled) {
    this.wikiEnabled = wikiEnabled;
  }

  public Boolean getShowMedia() {
    return showMedia;
  }

  public void setShowMedia(Boolean showMedia) {
    this.showMedia = showMedia;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSubmitText() {
    return submitText;
  }

  public void setSubmitText(String submitText) {
    this.submitText = submitText;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getHeaderImg() {
    return headerImg;
  }

  public void setHeaderImg(String headerImg) {
    this.headerImg = headerImg;
  }

  public String getDescriptionHtml() {
    return descriptionHtml;
  }

  public void setDescriptionHtml(String descriptionHtml) {
    this.descriptionHtml = descriptionHtml;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getCollapseDeletedComments() {
    return collapseDeletedComments;
  }

  public void setCollapseDeletedComments(Boolean collapseDeletedComments) {
    this.collapseDeletedComments = collapseDeletedComments;
  }

  public Boolean getOver18() {
    return over18;
  }

  public void setOver18(Boolean over18) {
    this.over18 = over18;
  }

  public String getPublicDescriptionHtml() {
    return publicDescriptionHtml;
  }

  public void setPublicDescriptionHtml(String publicDescriptionHtml) {
    this.publicDescriptionHtml = publicDescriptionHtml;
  }

  public Boolean getSpoilersEnabled() {
    return spoilersEnabled;
  }

  public void setSpoilersEnabled(Boolean spoilersEnabled) {
    this.spoilersEnabled = spoilersEnabled;
  }

  public List<Integer> getIconSize() {
    return iconSize;
  }

  public void setIconSize(List<Integer> iconSize) {
    this.iconSize = iconSize;
  }

  public Object getSuggestedCommentSort() {
    return suggestedCommentSort;
  }

  public void setSuggestedCommentSort(Object suggestedCommentSort) {
    this.suggestedCommentSort = suggestedCommentSort;
  }

  public String getIconImg() {
    return iconImg;
  }

  public void setIconImg(String iconImg) {
    this.iconImg = iconImg;
  }

  public String getHeaderTitle() {
    return headerTitle;
  }

  public void setHeaderTitle(String headerTitle) {
    this.headerTitle = headerTitle;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Object getUserIsMuted() {
    return userIsMuted;
  }

  public void setUserIsMuted(Object userIsMuted) {
    this.userIsMuted = userIsMuted;
  }

  public Object getSubmitLinkLabel() {
    return submitLinkLabel;
  }

  public void setSubmitLinkLabel(Object submitLinkLabel) {
    this.submitLinkLabel = submitLinkLabel;
  }

  public Object getAccountsActive() {
    return accountsActive;
  }

  public void setAccountsActive(Object accountsActive) {
    this.accountsActive = accountsActive;
  }

  public Boolean getPublicTraffic() {
    return publicTraffic;
  }

  public void setPublicTraffic(Boolean publicTraffic) {
    this.publicTraffic = publicTraffic;
  }

  public List<Integer> getHeaderSize() {
    return headerSize;
  }

  public void setHeaderSize(List<Integer> headerSize) {
    this.headerSize = headerSize;
  }

  public Integer getSubscribers() {
    return subscribers;
  }

  public void setSubscribers(Integer subscribers) {
    this.subscribers = subscribers;
  }

  public String getSubmitTextLabel() {
    return submitTextLabel;
  }

  public void setSubmitTextLabel(String submitTextLabel) {
    this.submitTextLabel = submitTextLabel;
  }

  public String getWhitelistStatus() {
    return whitelistStatus;
  }

  public void setWhitelistStatus(String whitelistStatus) {
    this.whitelistStatus = whitelistStatus;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  public Object getUserIsModerator() {
    return userIsModerator;
  }

  public void setUserIsModerator(Object userIsModerator) {
    this.userIsModerator = userIsModerator;
  }

  public String getKeyColor() {
    return keyColor;
  }

  public void setKeyColor(String keyColor) {
    this.keyColor = keyColor;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getCreated() {
    return created;
  }

  public void setCreated(Integer created) {
    this.created = created;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Boolean getQuarantine() {
    return quarantine;
  }

  public void setQuarantine(Boolean quarantine) {
    this.quarantine = quarantine;
  }

  public Boolean getHideAds() {
    return hideAds;
  }

  public void setHideAds(Boolean hideAds) {
    this.hideAds = hideAds;
  }

  public Integer getCreatedUtc() {
    return createdUtc;
  }

  public void setCreatedUtc(Integer createdUtc) {
    this.createdUtc = createdUtc;
  }

  public List<Integer> getBannerSize() {
    return bannerSize;
  }

  public void setBannerSize(List<Integer> bannerSize) {
    this.bannerSize = bannerSize;
  }

  public Object getUserIsContributor() {
    return userIsContributor;
  }

  public void setUserIsContributor(Object userIsContributor) {
    this.userIsContributor = userIsContributor;
  }

  public Object getAccountsActiveIsFuzzed() {
    return accountsActiveIsFuzzed;
  }

  public void setAccountsActiveIsFuzzed(Object accountsActiveIsFuzzed) {
    this.accountsActiveIsFuzzed = accountsActiveIsFuzzed;
  }

  public String getAdvertiserCategory() {
    return advertiserCategory;
  }

  public void setAdvertiserCategory(String advertiserCategory) {
    this.advertiserCategory = advertiserCategory;
  }

  public String getPublicDescription() {
    return publicDescription;
  }

  public void setPublicDescription(String publicDescription) {
    this.publicDescription = publicDescription;
  }

  public Boolean getShowMediaPreview() {
    return showMediaPreview;
  }

  public void setShowMediaPreview(Boolean showMediaPreview) {
    this.showMediaPreview = showMediaPreview;
  }

  public Integer getCommentScoreHideMins() {
    return commentScoreHideMins;
  }

  public void setCommentScoreHideMins(Integer commentScoreHideMins) {
    this.commentScoreHideMins = commentScoreHideMins;
  }

  public String getSubredditType() {
    return subredditType;
  }

  public void setSubredditType(String subredditType) {
    this.subredditType = subredditType;
  }

  public String getSubmissionType() {
    return submissionType;
  }

  public void setSubmissionType(String submissionType) {
    this.submissionType = submissionType;
  }

  public Object getUserIsSubscriber() {
    return userIsSubscriber;
  }

  public void setUserIsSubscriber(Object userIsSubscriber) {
    this.userIsSubscriber = userIsSubscriber;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(bannerImg);
    parcel.writeString(submitTextHtml);
    parcel.writeString(id);
    parcel.writeString(submitText);
    parcel.writeString(displayName);
    parcel.writeString(headerImg);
    parcel.writeString(descriptionHtml);
    parcel.writeString(title);
    parcel.writeString(publicDescriptionHtml);
    parcel.writeString(iconImg);
    parcel.writeString(headerTitle);
    parcel.writeString(description);
    parcel.writeString(submitTextLabel);
    parcel.writeString(whitelistStatus);
    parcel.writeString(lang);
    parcel.writeString(keyColor);
    parcel.writeString(name);
    parcel.writeString(url);
    parcel.writeString(advertiserCategory);
    parcel.writeString(publicDescription);
    parcel.writeString(subredditType);
    parcel.writeString(submissionType);
    parcel.writeInt(subscribers);
  }
}
