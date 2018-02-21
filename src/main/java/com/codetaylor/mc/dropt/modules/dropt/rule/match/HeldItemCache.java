package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

public class HeldItemCache {

  private final Map<String, ItemStack> map;

  public HeldItemCache(Map<String, ItemStack> map) {

    this.map = map;
  }

  @Nonnull
  public ItemStack get(String key) {

    ItemStack itemStack = this.map.get(key);

    if (itemStack == null) {
      return ItemStack.EMPTY;
    }

    return itemStack;
  }

  @ParametersAreNonnullByDefault
  public void put(String key, ItemStack value) {

    this.map.put(key, value);
  }

}
