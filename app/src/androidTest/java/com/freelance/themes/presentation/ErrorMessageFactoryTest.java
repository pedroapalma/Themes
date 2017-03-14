package com.freelance.themes.presentation;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.freelance.themes.R;
import com.freelance.themes.data.exception.NotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ErrorMessageFactoryTest {

  private Context appContext;

  @Before
  public void setUp() throws Exception {
    appContext = InstrumentationRegistry.getTargetContext();
  }

  @Test
  public void test_NetworkConnectionErrorMessage() {
    String expectedMessage = appContext.getString(R.string.connection_error);
    String actualMessage = ErrorMessageFactory.create(appContext, new IOException());
    assertEquals(actualMessage, expectedMessage);
  }

  @Test
  public void test_ServerErrorMessage() {
    String expectedMessage = appContext.getString(R.string.server_error);
    String actualMessage = ErrorMessageFactory.create(appContext, new HttpException(Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));
    assertEquals(actualMessage, expectedMessage);
  }

  @Test
  public void test_NotFoundErrorMessage() {
    String expectedMessage = appContext.getString(R.string.not_found);
    String actualMessage = ErrorMessageFactory.create(appContext, new NotFoundException());
    assertEquals(actualMessage, expectedMessage);
  }

}