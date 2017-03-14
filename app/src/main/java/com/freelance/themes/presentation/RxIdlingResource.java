package com.freelance.themes.presentation;

import android.support.test.espresso.idling.CountingIdlingResource;

public final class RxIdlingResource {

  public static int LOG_LEVEL_NONE = 0;
  public static int LOG_LEVEL_DEBUG = 1;
  private static final String TAG = RxIdlingResource.class.getSimpleName();
  private static int LOG_LEVEL = LOG_LEVEL_NONE;

  private static RxIdlingResource instance;

  private CountingIdlingResource idlingResource;

  public static void setLogLevel(int logLevel) {
    RxIdlingResource.LOG_LEVEL = logLevel;
  }

  public static void increment() {
    if (instance != null)
      getInstance().idlingResource.increment();
  }

  public static void decrement() {
    if (instance != null)
      getInstance().idlingResource.decrement();
  }

  public static boolean isIdleNow() {
    return getInstance().idlingResource.isIdleNow();
  }

  public CountingIdlingResource getIdlingResource() {
    return idlingResource;
  }

  private RxIdlingResource() {
    boolean debug = false;
    if (LOG_LEVEL == LOG_LEVEL_DEBUG) {
      debug = true;
    }
    idlingResource = new CountingIdlingResource(TAG, debug);
  }

  public static RxIdlingResource getInstance() {
    if (instance == null) {
      instance = new RxIdlingResource();
    }

    return instance;
  }
}