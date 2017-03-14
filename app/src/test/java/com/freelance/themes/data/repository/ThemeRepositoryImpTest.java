package com.freelance.themes.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ThemeRepositoryImpTest {

  private ThemeRepositoryImp themeRepositoryImp;

  @Mock
  private ThemeRepositoryFactory mockThemeRepositoryFactory;

  @Mock
  private ThemeService mockThemeService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    themeRepositoryImp = new ThemeRepositoryImp(mockThemeRepositoryFactory);
  }

  @Test
  public void testGetThemesFromRepository() {

    when(mockThemeRepositoryFactory.create()).thenReturn(mockThemeService);

    themeRepositoryImp.themes();

    verify(mockThemeRepositoryFactory).create();
    verify(mockThemeRepositoryFactory.create()).getThemeList();
  }
}