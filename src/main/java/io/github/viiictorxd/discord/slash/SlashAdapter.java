package io.github.viiictorxd.discord.slash;

import io.github.viiictorxd.discord.slash.adapter.TypeAdapter;

import java.util.HashMap;

public class SlashAdapter extends HashMap<Class<?>, TypeAdapter<?>> {

  public TypeAdapter<?> getTypeAdapter(Class<?> clazz) {
    return get(clazz);
  }

  public void registerTypeAdapter(Class<?> clazz, TypeAdapter<?> typeAdapter) {
    put(clazz, typeAdapter);
  }

  public void unregisterTypeAdapter(Class<?> clazz) {
    remove(clazz);
  }

  public boolean hasTypeAdapter(Class<?> clazz) {
    return containsKey(clazz);
  }
}
