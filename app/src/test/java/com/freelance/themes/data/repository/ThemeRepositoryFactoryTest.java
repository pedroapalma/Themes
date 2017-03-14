package com.freelance.themes.data.repository;

import com.freelance.themes.data.cache.DataCacheStorage;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ThemeRepositoryFactoryTest {

  private ThemeRepositoryFactory themeRepositoryFactory;

  @Mock
  private DataCacheStorage mockDataCacheStorage;

  @Mock
  ThemeService mockApiThemeService;

  @Mock
  ThemeService mockCacheThemeService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    themeRepositoryFactory = new ThemeRepositoryFactory(mockApiThemeService, mockCacheThemeService, mockDataCacheStorage);
  }

  @Test
  public void test_GetThemesDataFromCacheDataSource() throws Exception {
    when(mockDataCacheStorage.exists()).thenReturn(true);

    themeRepositoryFactory.create();

    verify(mockDataCacheStorage).exists();
  }

  @Test
  public void test_GetThemesDataFromApiDataSource() throws Exception {
    when(mockDataCacheStorage.exists()).thenReturn(false);

    themeRepositoryFactory.create();

    verify(mockDataCacheStorage).exists();
  }
}