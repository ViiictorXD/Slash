package io.github.viiictorxd.discord.slash;

import java.util.HashMap;

public class SlashMap extends HashMap<String, SlashData> {

  public SlashData getSlashData(String name) {
    return get(name.toLowerCase());
  }

  public void registerSlashData(String name, SlashData data) {
    put(name.toLowerCase(), data);
  }

  public void unregisterSlashData(String name) {
    remove(name.toLowerCase());
  }

  public boolean hasSlashData(String name) {
    return containsKey(name.toLowerCase());
  }
}
