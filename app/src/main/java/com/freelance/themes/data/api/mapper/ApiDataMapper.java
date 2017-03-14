package com.freelance.themes.data.api.mapper;

import com.freelance.themes.data.entity.ThemeEntity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ApiDataMapper implements ApiMapper {

  @Inject
  public ApiDataMapper() {
  }

  @Override
  public List<ThemeEntity> convert(JsonObject data) {
    List<ThemeEntity> themeList = new ArrayList<>();

    if (data != null) {
      JsonArray jsonArrayData = data.getAsJsonObject("data")
          .getAsJsonArray("children");
      for (int i = 0; i < jsonArrayData.size(); i++) {
        JsonObject jsonObjectData = jsonArrayData.get(i).getAsJsonObject();
        JsonElement jsonTheme = jsonObjectData.get("data");
        themeList.add(new Gson().fromJson(jsonTheme, ThemeEntity.class));
      }
    } else {
      return Collections.emptyList();
    }

    return themeList;
  }
}
