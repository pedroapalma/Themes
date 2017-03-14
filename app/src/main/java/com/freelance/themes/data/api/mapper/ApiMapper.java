package com.freelance.themes.data.api.mapper;

import com.freelance.themes.data.entity.ThemeEntity;
import com.google.gson.JsonObject;

import java.util.List;

public interface ApiMapper {
  List<ThemeEntity> convert(JsonObject data);
}
