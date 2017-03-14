package com.freelance.themes.data.cache;

import rx.Observable;

public interface DataCacheStorage<T> {

  Observable<T> getObservable();

  T get();

  void save(T data);

  boolean clear();

  boolean exists();
}
