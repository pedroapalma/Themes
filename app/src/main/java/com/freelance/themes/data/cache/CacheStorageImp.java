package com.freelance.themes.data.cache;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;

public class CacheStorageImp implements CacheStorage {

  private Context context;

  @Inject
  public CacheStorageImp(Context context) {
    this.context = context;
  }

  @Override
  public String get(String key) {
    BufferedReader input;
    File file;
    try {
      file = new File(context.getFilesDir(), key);

      input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      String line;
      StringBuffer buffer = new StringBuffer();
      while ((line = input.readLine()) != null) {
        buffer.append(line);
      }

      return buffer.toString();

    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public boolean save(String key, String data) {
    FileOutputStream outputStream;

    try {
      outputStream = context.openFileOutput(key, Context.MODE_PRIVATE);
      outputStream.write(data.getBytes());
      outputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public boolean clear(String key) {
    File dir = context.getFilesDir();
    File file = new File(dir, key);
    return file.delete();
  }

  @Override
  public boolean exists(String key) {
    File dir = context.getFilesDir();
    File file = new File(dir, key);
    return file.exists();
  }

}
