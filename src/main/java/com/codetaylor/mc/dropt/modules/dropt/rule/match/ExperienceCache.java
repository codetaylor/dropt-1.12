package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class ExperienceCache {

  private final Map<String, Integer> map;

  public ExperienceCache(Map<String, Integer> map) {

    this.map = map;
  }

  public int get(@Nullable String key) {

    if (key == null) {
      return 0;
    }

    Integer integer = this.map.get(key);

    if (integer == null) {
      return 0;
    }

    return integer;
  }

  public void put(@Nonnull String key, int value) {

    this.map.put(key, value);
  }

}
