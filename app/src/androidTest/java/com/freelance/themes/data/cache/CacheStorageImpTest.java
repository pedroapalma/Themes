package com.freelance.themes.data.cache;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.freelance.themes.data.cache.serializer.JsonSerializer;
import com.freelance.themes.data.entity.ThemeEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CacheStorageImpTest {

  private CacheStorageImp cacheStorageImp;

  private static String savedThemesData = "";

  private String fakeJsonThemeEntityList;

  @BeforeClass
  public static void setUpBeforeClass() {
    Context appContext = InstrumentationRegistry.getTargetContext();
    CacheStorageImp cacheStorageImp = new CacheStorageImp(appContext);
    if (cacheStorageImp.exists(ThemesCacheStorage.KEY))
      savedThemesData = cacheStorageImp.get(ThemesCacheStorage.KEY);
    cacheStorageImp.clear(ThemesCacheStorage.KEY);
  }

  @Before
  public void setUp() throws Exception {
    Context appContext = InstrumentationRegistry.getTargetContext();
    cacheStorageImp = new CacheStorageImp(appContext);
    List<ThemeEntity> fakeThemeEntityList = createFakeThemeEntityList();
    fakeJsonThemeEntityList = new JsonSerializer().serialize(fakeThemeEntityList);
  }

  @Test
  public void test_Success_CacheStorage() {
    boolean wasSaved = cacheStorageImp.save(ThemesCacheStorage.KEY, fakeJsonThemeEntityList);
    boolean exists = cacheStorageImp.exists(ThemesCacheStorage.KEY);
    String savedThemes = cacheStorageImp.get(ThemesCacheStorage.KEY);
    boolean wasCleaned = cacheStorageImp.clear(ThemesCacheStorage.KEY);

    assertEquals(wasSaved, true);
    assertEquals(exists, true);
    assertEquals(savedThemes, fakeJsonThemeEntityList);
    assertEquals(wasCleaned, true);
  }

  @After
  public void tearDown() throws Exception {
    if (!TextUtils.isEmpty(savedThemesData))
      cacheStorageImp.save(ThemesCacheStorage.KEY, savedThemesData);
  }

  private List<ThemeEntity> createFakeThemeEntityList() {
    List<ThemeEntity> themeEntityList = new ArrayList<>();
    themeEntityList.add(new ThemeEntity("http://image_one.png", "Text One"));
    themeEntityList.add(new ThemeEntity("http://image_two.png", "Text Two"));
    return themeEntityList;
  }

}