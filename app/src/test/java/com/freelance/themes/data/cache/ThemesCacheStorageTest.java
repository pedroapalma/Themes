package com.freelance.themes.data.cache;

import com.freelance.themes.data.cache.serializer.JsonSerializer;
import com.freelance.themes.data.entity.ThemeEntity;
import com.freelance.themes.data.exception.NotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import rx.observers.TestSubscriber;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ThemesCacheStorageTest {

  private ThemesCacheStorage themesCacheStorage;

  @Mock
  private CacheStorage mockCacheStorage;

  @Mock
  private Executor mockExecutor;

  @Mock
  private JsonSerializer mockJsonSerializer;

  @Captor
  private ArgumentCaptor<Runnable> runnableCaptor;

  private List<ThemeEntity> fakeThemeList;

  private String fakeJsonThemeList;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    JsonSerializer jsonSerializer = new JsonSerializer();
    fakeThemeList = createFakeThemeList();
    fakeJsonThemeList = jsonSerializer.serialize(fakeThemeList);
    themesCacheStorage = new ThemesCacheStorage(mockCacheStorage, mockExecutor, mockJsonSerializer);
  }

  private List<ThemeEntity> createFakeThemeList() {
    List<ThemeEntity> themeList = new ArrayList<>();
    themeList.add(new ThemeEntity("http://image_one.png", "Text One"));
    themeList.add(new ThemeEntity("http://image_two.png", "Text Two"));
    return themeList;
  }

  @Test
  public void test_SuccessCase_GetThemesDataFromCacheStorage() {
    when(mockCacheStorage.get(ThemesCacheStorage.KEY)).thenReturn(fakeJsonThemeList);
    when(mockJsonSerializer.deserializeList(fakeJsonThemeList, ThemeEntity.class)).thenReturn(fakeThemeList);

    List<ThemeEntity> themeList = themesCacheStorage.get();

    verify(mockCacheStorage).get(ThemesCacheStorage.KEY);
    verify(mockJsonSerializer).deserializeList(fakeJsonThemeList, ThemeEntity.class);
    assertEquals(2, themeList.size());
    assertEquals(fakeThemeList.size(), themeList.size());
    assertEquals(fakeThemeList.get(0).getBannerImg(), themeList.get(0).getBannerImg());
    assertEquals(fakeThemeList.get(0).getSubmitText(), themeList.get(0).getSubmitText());
    assertEquals(fakeThemeList.get(1).getBannerImg(), themeList.get(1).getBannerImg());
    assertEquals(fakeThemeList.get(1).getSubmitText(), themeList.get(1).getSubmitText());
  }

  @Test
  public void test_SuccessCase_GetObservableThemesDataFromCacheStorage() {
    when(mockCacheStorage.get(ThemesCacheStorage.KEY)).thenReturn(fakeJsonThemeList);
    when(mockJsonSerializer.deserializeList(fakeJsonThemeList, ThemeEntity.class)).thenReturn(fakeThemeList);


    TestSubscriber<List<ThemeEntity>> subscriber = new TestSubscriber<>();
    themesCacheStorage.getObservable().subscribe(subscriber);

    subscriber.awaitTerminalEvent();
    subscriber.assertNoErrors();
    List<List<ThemeEntity>> onNextEvents = subscriber.getOnNextEvents();
    List<ThemeEntity> themeList = onNextEvents.get(0);

    verify(mockCacheStorage).get(ThemesCacheStorage.KEY);
    verify(mockJsonSerializer).deserializeList(fakeJsonThemeList, ThemeEntity.class);
    assertEquals(themeList.size(), 2);
    assertEquals(fakeThemeList.size(), themeList.size());
    assertEquals(fakeThemeList.get(0).getBannerImg(), themeList.get(0).getBannerImg());
    assertEquals(fakeThemeList.get(0).getSubmitText(), themeList.get(0).getSubmitText());
    assertEquals(fakeThemeList.get(1).getBannerImg(), themeList.get(1).getBannerImg());
    assertEquals(fakeThemeList.get(1).getSubmitText(), themeList.get(1).getSubmitText());
  }

  @Test
  public void test_SuccessCase_SaveThemeDataIntoCacheStorage() {
    when(mockCacheStorage.save(ThemesCacheStorage.KEY, fakeJsonThemeList)).thenReturn(true);
    when(mockJsonSerializer.serialize(fakeThemeList)).thenReturn(fakeJsonThemeList);

    themesCacheStorage.save(fakeThemeList);

    verify(mockJsonSerializer).serialize(fakeThemeList);
    verify(mockExecutor).execute(runnableCaptor.capture());
    runnableCaptor.getValue().run();
    verify(mockCacheStorage).save(ThemesCacheStorage.KEY, fakeJsonThemeList);
    assertTrue(themesCacheStorage.isSaved());
  }

  @Test
  public void test_SuccessCase_ClearThemesDataFromCacheStorage() {
    when(mockCacheStorage.exists(ThemesCacheStorage.KEY)).thenReturn(true);
    when(mockCacheStorage.clear(ThemesCacheStorage.KEY)).thenReturn(true);

    boolean hasCleanedThemeData = themesCacheStorage.clear();

    verify(mockCacheStorage).exists(ThemesCacheStorage.KEY);
    verify(mockCacheStorage).clear(ThemesCacheStorage.KEY);
    assertTrue(hasCleanedThemeData);
  }

  @Test
  public void test_SuccessCase_ExistsThemesDataIntoCacheStorage() {
    when(mockCacheStorage.exists(ThemesCacheStorage.KEY)).thenReturn(true);

    boolean existsThemesDataIntoCache = themesCacheStorage.exists();

    verify(mockCacheStorage).exists(ThemesCacheStorage.KEY);
    assertTrue(existsThemesDataIntoCache);
  }

  @Test
  public void test_FailureCase_GetThemesDataFromCacheStorage() {
    when(mockCacheStorage.get(ThemesCacheStorage.KEY)).thenReturn(null);

    List<ThemeEntity> themeList = themesCacheStorage.get();

    verify(mockCacheStorage).get(ThemesCacheStorage.KEY);
    assertNull("checking that not exists themes data into cache", themeList);
  }

  @Test
  public void test_NotFoundException_GetObservableThemesDataFromCacheStorage() {
    when(mockCacheStorage.get(ThemesCacheStorage.KEY)).thenReturn(null);

    TestSubscriber<List<ThemeEntity>> subscriber = new TestSubscriber<>();
    themesCacheStorage.getObservable().subscribe(subscriber);

    subscriber.awaitTerminalEvent();
    subscriber.assertError(NotFoundException.class);
  }

  @Test
  public void test_FailureCase_SaveThemesDataIntoCacheStorage() {
    when(mockCacheStorage.save(ThemesCacheStorage.KEY, fakeJsonThemeList)).thenReturn(false);
    when(mockJsonSerializer.serialize(fakeThemeList)).thenReturn(fakeJsonThemeList);

    themesCacheStorage.save(fakeThemeList);

    verify(mockJsonSerializer).serialize(fakeThemeList);
    verify(mockExecutor).execute(runnableCaptor.capture());
    runnableCaptor.getValue().run();
    verify(mockCacheStorage).save(ThemesCacheStorage.KEY, fakeJsonThemeList);
    assertFalse(themesCacheStorage.isSaved());
  }

  @Test
  public void test_FailureCase_ClearThemesDataFromCacheStorageWhenExistsData() {
    when(mockCacheStorage.exists(ThemesCacheStorage.KEY)).thenReturn(true);
    when(mockCacheStorage.clear(ThemesCacheStorage.KEY)).thenReturn(false);

    boolean hasCleanedThemeData = themesCacheStorage.clear();

    verify(mockCacheStorage).exists(ThemesCacheStorage.KEY);
    verify(mockCacheStorage).clear(ThemesCacheStorage.KEY);
    assertFalse(hasCleanedThemeData);

  }

  @Test
  public void test_FailureCase_ClearThemesDataFromCacheStorageWhenNotExistsData() {
    when(mockCacheStorage.exists(ThemesCacheStorage.KEY)).thenReturn(false);
    when(mockCacheStorage.clear(ThemesCacheStorage.KEY)).thenReturn(false);

    boolean hasCleanedThemeData = themesCacheStorage.clear();

    verify(mockCacheStorage).exists(ThemesCacheStorage.KEY);
    assertFalse(hasCleanedThemeData);
  }

  @Test
  public void test_FailureCase_ExistsThemesDataIntoCacheStorage() {
    when(mockCacheStorage.exists(ThemesCacheStorage.KEY)).thenReturn(false);

    boolean existsThemesDataIntoCache = themesCacheStorage.exists();

    assertFalse(existsThemesDataIntoCache);
  }
}