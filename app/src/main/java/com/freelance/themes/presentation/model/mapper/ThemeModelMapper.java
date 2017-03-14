package com.freelance.themes.presentation.model.mapper;

import com.freelance.themes.data.entity.ThemeEntity;
import com.freelance.themes.presentation.model.ThemeModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ThemeModelMapper {

  @Inject
  public ThemeModelMapper() {
  }

  public ThemeModel convert(ThemeEntity themeEntity) {
    ThemeModel themeModel = null;
    if (themeEntity != null) {
      themeModel = new ThemeModel();
      themeModel.setSubmitText(themeEntity.getSubmitText());
      themeModel.setName(themeEntity.getName());
      themeModel.setBannerImg(themeEntity.getBannerImg());
      themeModel.setHeaderImg(themeEntity.getHeaderImg());
      themeModel.setIconImg(themeEntity.getIconImg());
      themeModel.setAdvertiserCategory(themeEntity.getAdvertiserCategory());
      themeModel.setTitle(themeEntity.getTitle());
      themeModel.setHeaderTitle(themeEntity.getHeaderTitle());
      themeModel.setDisplayName(themeEntity.getDisplayName());
      themeModel.setPublicDescription(themeEntity.getPublicDescription());
      themeModel.setDescription(themeEntity.getDescription());
      themeModel.setDescriptionHtml(themeEntity.getDescriptionHtml());
      themeModel.setSubredditType(themeEntity.getSubredditType());
      themeModel.setPublicDescriptionHtml(themeEntity.getPublicDescriptionHtml());
      themeModel.setKeyColor(themeEntity.getKeyColor());
      themeModel.setUrl(themeEntity.getUrl());
      themeModel.setSubscribers(themeEntity.getSubscribers());
    }

    return themeModel;
  }

  public ArrayList<ThemeModel> convert(List<ThemeEntity> themeEntityCollection) {
    ArrayList<ThemeModel> themeModelList = new ArrayList<>();
    ThemeModel themeModel;
    for (ThemeEntity themeEntity : themeEntityCollection) {
      themeModel = convert(themeEntity);
      if (themeModel != null) {
        themeModelList.add(themeModel);
      }
    }

    return themeModelList;
  }
}
