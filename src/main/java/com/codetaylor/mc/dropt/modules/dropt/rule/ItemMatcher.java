package com.codetaylor.mc.dropt.modules.dropt.rule;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMatcher {

  private String domain;
  private String path;
  private int meta;
  private int[] metas;

  public ItemMatcher(String domain, String path, int meta, int[] metas) {

    this.domain = domain;
    this.path = path;
    this.meta = meta;
    this.metas = metas;
  }

  public boolean matches(ItemStack itemStack) {

    ResourceLocation registryName = itemStack.getItem().getRegistryName();

    if (registryName == null) {
      return false;
    }

    if (!registryName.getResourceDomain().equals(this.domain)) {
      return false;
    }

    if (!registryName.getResourcePath().equals(this.path)) {
      return false;
    }

    int itemMeta = itemStack.getMetadata();

    if (this.meta == OreDictionary.WILDCARD_VALUE
        || this.meta == itemMeta) {
      return true;
    }

    for (int meta : this.metas) {

      if (meta == OreDictionary.WILDCARD_VALUE
          || meta == itemMeta) {
        return true;
      }
    }

    return false;
  }
}
