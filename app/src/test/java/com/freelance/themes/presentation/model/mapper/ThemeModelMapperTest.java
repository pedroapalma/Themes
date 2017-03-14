package com.freelance.themes.presentation.model.mapper;

import com.freelance.themes.data.entity.ThemeEntity;
import com.freelance.themes.presentation.model.ThemeModel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

public class ThemeModelMapperTest {

  private final String FAKE_BANNER_IMG = "http://b.thumbs.redditmedia.com/PXt8GnqdYu-9lgzb3iesJBLN21bXExRV1A45zdw4sYE.png";
  private final String FAKE_SUBMIT_TEXT = "AskReddit is all about DISCUSSION";

  private ThemeModelMapper themeModelMapper;

  @Before
  public void setUp() throws Exception {
    themeModelMapper = new ThemeModelMapper();
  }

  @Test
  public void test_ConvertThemeEntityToThemeModel() {

    ThemeEntity fakeThemeEntity = createFakeThemeEntity();

    ThemeModel themeModel = themeModelMapper.convert(fakeThemeEntity);

    assertThat(themeModel, is(instanceOf(ThemeModel.class)));
    assertEquals(themeModel.getBannerImg(), FAKE_BANNER_IMG);
    assertEquals(themeModel.getSubmitText(), FAKE_SUBMIT_TEXT);
  }

  @Test
  public void test_ConvertThemeEntityCollectionToThemeModelCollection() {
    List<ThemeEntity> fakeThemeEntityList = createFakeThemeEntityList();

    ArrayList<ThemeModel> themeModelList = themeModelMapper.convert(fakeThemeEntityList);

    assertEquals(2, themeModelList.size());
    assertThat(themeModelList.get(0), is(instanceOf(ThemeModel.class)));
    assertThat(themeModelList.get(1), is(instanceOf(ThemeModel.class)));
    assertEquals("http://image_one.png", themeModelList.get(0).getBannerImg());
    assertEquals("Text One", themeModelList.get(0).getSubmitText());
    assertEquals("http://image_two.png", themeModelList.get(1).getBannerImg());
    assertEquals("Text Two", themeModelList.get(1).getSubmitText());
  }

  private ThemeEntity createFakeThemeEntity() {
    ThemeEntity themeEntity = new ThemeEntity();
    themeEntity.setBannerImg(FAKE_BANNER_IMG);
    themeEntity.setSubmitText(FAKE_SUBMIT_TEXT);
    return themeEntity;
  }

  private List<ThemeEntity> createFakeThemeEntityList() {
    List<ThemeEntity> themeEntityList = new ArrayList<>();
    themeEntityList.add(new ThemeEntity("http://image_one.png", "Text One"));
    themeEntityList.add(new ThemeEntity("http://image_two.png", "Text Two"));
    return themeEntityList;
  }
}