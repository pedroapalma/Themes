package com.freelance.themes.presentation.presenter;

import com.freelance.themes.data.entity.ThemeEntity;
import com.freelance.themes.data.repository.ThemeRepository;
import com.freelance.themes.presentation.contract.ThemesContract;
import com.freelance.themes.presentation.model.ThemeModel;
import com.freelance.themes.presentation.model.mapper.ThemeModelMapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ThemesPresenterTest {

  private ThemesPresenter themesPresenter;

  @Mock
  private ThemeRepository mockThemeRepository;

  @Mock
  private ThemeModelMapper mockThemeModelMapper;

  @Mock
  private ThemesContract.View mockView;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    themesPresenter = new ThemesPresenter(mockThemeRepository, Schedulers.immediate(), Schedulers.immediate(), mockThemeModelMapper);
    themesPresenter.attachView(mockView);
  }

  @Test
  public void test_SuccessCase_GetThemesFromRepositoryAndShowIntoTheView() {
    List<ThemeEntity> fakeThemeEntityList = createFakeThemeEntityList();
    ArrayList<ThemeModel> fakeThemeModelList = new ArrayList<>();
    when(mockThemeRepository.themes()).thenReturn(Observable.just(fakeThemeEntityList));

    themesPresenter.loadThemes();

    verify(mockThemeRepository).themes();
    verify(mockView).hideError();
    verify(mockView).setProgressIndicator(true);
    verify(mockView).setProgressIndicator(false);
    verify(mockView).showThemes(fakeThemeModelList);
    verify(mockView, never()).showError(any(String.class));
    verify(mockThemeModelMapper).convert(fakeThemeEntityList);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void test_IOException_GetThemesFromRepositoryAndShowIntoTheView() {
    ArrayList<ThemeModel> fakeThemeModelList = new ArrayList<>();
    when(mockThemeRepository.themes()).thenReturn(getIOExceptionError());

    themesPresenter.loadThemes();

    verify(mockThemeRepository).themes();
    verify(mockView).hideError();
    verify(mockView).setProgressIndicator(true);
    verify(mockView).setProgressIndicator(false);
    verify(mockView, never()).showThemes(fakeThemeModelList);
    verify(mockView).showError("Connection error");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void test_403ForbiddenError_GetThemesFromRepositoryAndShowIntoTheView() {
    ArrayList<ThemeModel> fakeThemeModelList = new ArrayList<>();
    when(mockThemeRepository.themes()).thenReturn(get403ForbiddenError());

    themesPresenter.loadThemes();

    verify(mockThemeRepository).themes();
    verify(mockView).hideError();
    verify(mockView).setProgressIndicator(true);
    verify(mockView).setProgressIndicator(false);
    verify(mockView, never()).showThemes(fakeThemeModelList);
    verify(mockView).showError("Server error");
  }

  @Test
  public void test_SuccessCase_OpenThemeDetailAndShowIntoTheView() {
    ThemeModel fakeThemeModel = new ThemeModel();

    themesPresenter.openThemeDetail(fakeThemeModel);

    verify(mockView).showThemeDetail(fakeThemeModel);
  }

  @Test
  public void test_SuccessCase_OpenUrl() {
    String fakeUrl = "https://www.reddit.com/r/Overwatch/";

    themesPresenter.openUrl(fakeUrl);

    verify(mockView).showUrl(fakeUrl);
  }

  private List<ThemeEntity> createFakeThemeEntityList() {
    List<ThemeEntity> themeEntityList = new ArrayList<>();
    themeEntityList.add(new ThemeEntity("http://image_one.png", "Text One"));
    themeEntityList.add(new ThemeEntity("http://image_two.png", "Text Two"));
    return themeEntityList;
  }

  private Observable getIOExceptionError() {
    return Observable.error(new IOException());
  }

  private Observable get403ForbiddenError() {
    return Observable.error(new HttpException(
        Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));
  }
}