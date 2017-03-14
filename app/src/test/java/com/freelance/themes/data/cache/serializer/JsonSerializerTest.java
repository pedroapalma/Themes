package com.freelance.themes.data.cache.serializer;

import com.freelance.themes.data.entity.ThemeEntity;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JsonSerializerTest {

  private static final String FAKE_JSON_RESPONSE = "{\n"
      + "    \"banner_img\": \"http://b.thumbs.redditmedia.com/PXt8GnqdYu-9lgzb3iesJBLN21bXExRV1A45zdw4sYE.png\",\n"
      + "    \"submit_text\": \"AskReddit is all about DISCUSSION\"\n"
      + "}";

  private static final String FAKE_JSON_ARRAY_RESPONSE = "[{\n"
      + "    \"banner_img\": \"http://image_one.png\",\n"
      + "    \"submit_text\": \"Text One\"\n"
      + "},{\n"
      + "    \"banner_img\": \"http://image_two.png\",\n"
      + "    \"submit_text\": \"Text Two\"\n"
      +"}]";

  private JsonSerializer jsonSerializer;

  @Before
  public void setUp() throws Exception {
    jsonSerializer = new JsonSerializer();
  }

  @Test
  public void test_SerializeAndDeserializeJsonData() {
    ThemeEntity themeEntityOne = jsonSerializer.deserialize(FAKE_JSON_RESPONSE, ThemeEntity.class);
    List<ThemeEntity> themeEntityListOne = jsonSerializer.deserializeList(FAKE_JSON_ARRAY_RESPONSE, ThemeEntity.class);

    String jsonTheme = jsonSerializer.serialize(themeEntityOne);
    String jsonArrayTheme = jsonSerializer.serialize(themeEntityListOne);

    ThemeEntity themeEntityTwo = jsonSerializer.deserialize(jsonTheme, ThemeEntity.class);
    List<ThemeEntity> themeEntityListTwo = jsonSerializer.deserializeList(jsonArrayTheme, ThemeEntity.class);

    assertEquals(themeEntityOne.getBannerImg(), themeEntityTwo.getBannerImg());
    assertEquals(themeEntityOne.getSubmitText(), themeEntityTwo.getSubmitText());
    assertEquals(2, themeEntityListOne.size());
    assertEquals(2, themeEntityListTwo.size());
    assertEquals(themeEntityListOne.get(0).getBannerImg(), themeEntityListTwo.get(0).getBannerImg());
    assertEquals(themeEntityListOne.get(0).getSubmitText(), themeEntityListTwo.get(0).getSubmitText());
    assertEquals(themeEntityListOne.get(1).getBannerImg(), themeEntityListTwo.get(1).getBannerImg());
    assertEquals(themeEntityListOne.get(1).getSubmitText(), themeEntityListTwo.get(1).getSubmitText());
  }

}