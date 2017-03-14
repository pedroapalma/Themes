package com.freelance.themes.data.repository.datasource;

import com.freelance.themes.data.api.RedditApiService;
import com.freelance.themes.data.api.mapper.ApiDataMapper;
import com.freelance.themes.data.api.mapper.ApiMapper;
import com.freelance.themes.data.cache.DataCacheStorage;
import com.freelance.themes.data.entity.ThemeEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApiThemeServiceTest {

  private final String FAKE_BANNER_IMG = "http://b.thumbs.redditmedia.com/PXt8GnqdYu-9lgzb3iesJBLN21bXExRV1A45zdw4sYE.png";
  private final String FAKE_SUBMIT_TEXT = "AskReddit is all about DISCUSSION";

  private ApiThemeService apiThemeService;

  @Mock
  private RedditApiService mockRedditApiService;

  @Mock
  private ApiMapper mockApiMapper;

  @Mock
  private DataCacheStorage mockDataCacheStorage;

  private JsonObject fakeApiData;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    fakeApiData = createFakeApiData();
    apiThemeService = new ApiThemeService(mockRedditApiService, mockApiMapper, mockDataCacheStorage);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void test_SuccessCase_GetThemesDataFromApi() {
    when(mockRedditApiService.getThemeData()).thenReturn(Observable.just(fakeApiData));
    when(mockApiMapper.convert(fakeApiData)).thenReturn(new ApiDataMapper().convert(fakeApiData));

    TestSubscriber<List<ThemeEntity>> subscriber = new TestSubscriber<>();
    apiThemeService.getThemeList().subscribe(subscriber);

    subscriber.awaitTerminalEvent();
    subscriber.assertNoErrors();
    List<List<ThemeEntity>> onNextEvents = subscriber.getOnNextEvents();
    List<ThemeEntity> themeEntityList = onNextEvents.get(0);
    verify(mockRedditApiService).getThemeData();
    verify(mockApiMapper).convert(fakeApiData);
    verify(mockDataCacheStorage).save(themeEntityList);
    assertEquals(FAKE_BANNER_IMG, themeEntityList.get(0).getBannerImg());
    assertEquals(FAKE_SUBMIT_TEXT, themeEntityList.get(0).getSubmitText());
  }

  @Test
  @SuppressWarnings("unchecked")
  public void test_IOException_GetThemesDataFromApi() {
    when(mockRedditApiService.getThemeData()).thenReturn(getIOExceptionError());

    TestSubscriber<List<ThemeEntity>> subscriber = new TestSubscriber<>();
    apiThemeService.getThemeList().subscribe(subscriber);

    subscriber.awaitTerminalEvent();
    subscriber.assertError(IOException.class);
    verify(mockRedditApiService).getThemeData();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void test_HttpException_GetThemesDataFromApi() {
    when(mockRedditApiService.getThemeData()).thenReturn(get403ForbiddenError());

    TestSubscriber<List<ThemeEntity>> subscriber = new TestSubscriber<>();
    apiThemeService.getThemeList().subscribe(subscriber);

    subscriber.awaitTerminalEvent();
    subscriber.assertError(HttpException.class);
    verify(mockRedditApiService).getThemeData();
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

  private Observable getIOExceptionError() {
    return Observable.error(new IOException());
  }

  private Observable get403ForbiddenError() {
    return Observable.error(new HttpException(
        Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));
  }
}