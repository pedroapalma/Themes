package com.freelance.themes.data.exception;

public class UnexpectedException extends Exception {
  public UnexpectedException(String message) {
    super(message);
  }

  public UnexpectedException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnexpectedException(Throwable cause) {
    super(cause);
  }
}
