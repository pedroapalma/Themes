package com.freelance.themes.data.api;

import com.freelance.themes.data.api.mapper.ApiDataMapper;
import com.freelance.themes.data.entity.ThemeEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ApiDataMapperTest {

  private final String FAKE_BANNER_IMG = "http://b.thumbs.redditmedia.com/PXt8GnqdYu-9lgzb3iesJBLN21bXExRV1A45zdw4sYE.png";
  private final String FAKE_SUBMIT_TEXT = "AskReddit is all about DISCUSSION";
  
  private ApiDataMapper apiDataMapper;

  @Before
  public void setUp() throws Exception {
    apiDataMapper = new ApiDataMapper();
  }

  @Test
  public void test_ConvertApiDataFromJsonToThemeEntityObject() {
    JsonObject fakeApiData = createFakeApiData();

    List<ThemeEntity> themeEntityList = apiDataMapper.convert(fakeApiData);

    assertEquals(1, themeEntityList.size());
    assertEquals(FAKE_BANNER_IMG, themeEntityList.get(0).getBannerImg());
    assertEquals(FAKE_SUBMIT_TEXT, themeEntityList.get(0).getSubmitText());

  }

  private JsonObject createFakeApiData() {
    JsonObject fakeApiDAta = new JsonObject();

    JsonObject jsonTheme = new JsonObject();
    jsonTheme.addProperty("banner_img", FAKE_BANNER_IMG);
    jsonTheme.addProperty("submit_text", FAKE_SUBMIT_TEXT);

    JsonObject jsonWrapperTheme = new JsonObject();
    jsonWrapperTheme.add("data", jsonTheme);
    jsonWrapperTheme.addProperty("kind", "t5");

    JsonArray jsonArrayTheme = new JsonArray();
    jsonArrayTheme.add(jsonWrapperTheme);

    JsonObject jsonData = new JsonObject();
    jsonData.addProperty("modhash", "");
    jsonData.add("children", jsonArrayTheme);
    jsonData.addProperty("after", "t5_2qh61");
    jsonData.add("before", null);

    fakeApiDAta.addProperty("kind", "Listing");
    fakeApiDAta.add("data", jsonData);

    return  fakeApiDAta;
  }

}