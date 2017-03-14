package com.freelance.themes.data.cache;

public interface CacheStorage {

  String get(String key);

  boolean save(String key, String data);

  boolean clear(String key);

  boolean exists(String key);
}
