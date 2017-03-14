package com.freelance.themes.presentation;

import android.content.Context;

import com.freelance.themes.R;
import com.freelance.themes.data.exception.NotFoundException;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

public class ErrorMessageFactory {

  private static final String CONNECTION_ERROR = "Connection error";
  private static final String SERVER_ERROR = "Server error";
  private static final String UNEXPECTED_ERROR = "Unexpected error";
  private static final String NOT_FOUND = "Not found";

  public static String create(Context context, Throwable e) {
    if (e instanceof IOException)
      return context != null ? context.getString(R.string.connection_error) : CONNECTION_ERROR;
    else if (e instanceof HttpException)
      return context != null ? context.getString(R.string.server_error) : SERVER_ERROR;
    else if (e instanceof NotFoundException)
      return context != null ? context.getString(R.string.not_found) : NOT_FOUND;
    else
      return context != null ? context.getString(R.string.unexpected_error) : UNEXPECTED_ERROR;
  }
}
