package com.freelance.themes.data.cache.serializer;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class JsonSerializer {
  private final Gson gson = new Gson();

  @Inject
  public JsonSerializer() {
  }

  public String serialize(Object data) {
    String jsonString = gson.toJson(data);
    return jsonString;
  }

  public <T> T deserialize(String json, Class<T> cl) {
    T object = gson.fromJson(json, new Element<T>(cl));
    return object;
  }

  public <T> List<T> deserializeList(String json, Class<T> cl) {
    List<T> list = gson.fromJson(json, new ListWithElements<T>(cl));
    return list;
  }

  private class ListWithElements<T> implements ParameterizedType {

    private Class<T> elementsClass;

    public ListWithElements(Class<T> elementsClass) {
      this.elementsClass = elementsClass;
    }

    public Type[] getActualTypeArguments() {
      return new Type[] {elementsClass};
    }

    public Type getRawType() {
      return List.class;
    }

    public Type getOwnerType() {
      return null;
    }
  }

  private class Element<T> implements ParameterizedType {

    private Class<T> cl;

    public Element(Class<T> cl) {
      this.cl = cl;
    }

    public Type[] getActualTypeArguments() {
      return new Type[] {cl};
    }

    public Type getRawType() {
      return cl;
    }

    public Type getOwnerType() {
      return null;
    }
  }

}
