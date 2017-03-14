package com.freelance.themes.data.repository.datasource;

import com.freelance.themes.data.cache.DataCacheStorage;
import com.freelance.themes.data.entity.ThemeEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CacheThemeServiceTest {

  private CacheThemeService cacheThemeService;

  @Mock
  private DataCacheStorage mockDataCacheStorage;

  private List<ThemeEntity> fakeThemeList;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    fakeThemeList = createFakeThemeList();
    cacheThemeService = new CacheThemeService(mockDataCacheStorage);
  }

  @Test
  public void test_successCase_getThemesDataFromCacheStorage() {
    when(mockDataCacheStorage.getObservable()).thenReturn(Observable.just(fakeThemeList));

    TestSubscriber<List<ThemeEntity>> subscriber = new TestSubscriber<>();
    cacheThemeService.getThemeList().subscribe(subscriber);

    subscriber.awaitTerminalEvent();
    subscriber.assertNoErrors();
    List<List<ThemeEntity>> onNextEvents = subscriber.getOnNextEvents();
    List<ThemeEntity> themeEntityList = onNextEvents.get(0);
    verify(mockDataCacheStorage).getObservable();
    assertEquals("http://image_one.png", themeEntityList.get(0).getBannerImg());
    assertEquals("Text One", themeEntityList.get(0).getSubmitText());
    assertEquals("http://image_two.png", themeEntityList.get(1).getBannerImg());
    assertEquals("Text Two", themeEntityList.get(1).getSubmitText());

  }

  private List<ThemeEntity> createFakeThemeList() {
    List<ThemeEntity> themeList = new ArrayList<>();
    themeList.add(new ThemeEntity("http://image_one.png", "Text One"));
    themeList.add(new ThemeEntity("http://image_two.png", "Text Two"));
    return themeList;
  }
}