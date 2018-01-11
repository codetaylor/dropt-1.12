package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.modules.dropt.rule.log.LogFileWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

public class ItemMatchEntry {

  private String domain;
  private String path;
  private int meta;
  private int[] metas;

  public ItemMatchEntry(String domain, String path, int meta, int[] metas) {

    this.domain = domain;
    this.path = path;
    this.meta = meta;
    this.metas = metas;
  }

  public boolean matches(ItemStack itemStack, LogFileWrapper logFile, boolean debug, String logPrefix) {

    if (debug) {
      logFile.debug(String.format(
          "%s[--] Attempting to match candidate [%s] with: [%s]",
          logPrefix,
          itemStack,
          this.toString()
      ));
    }

    ResourceLocation registryName = itemStack.getItem().getRegistryName();

    if (registryName == null) {

      if (debug) {
        logFile.debug(logPrefix + "[!!] No registry name for match candidate: " + itemStack);
      }
      return false;
    }

    if (!registryName.getResourceDomain().equals(this.domain)) {

      if (debug) {
        logFile.debug(String.format(
            "%s[!!] Domain mismatch: (match) %s != %s (candidate)",
            logPrefix,
            this.domain,
            registryName.getResourceDomain()
        ));
      }
      return false;

    } else if (debug) {
      logFile.debug(String.format(
          "%s[OK] Domain match: (match) %s == %s (candidate)",
          logPrefix,
          this.domain,
          registryName.getResourceDomain()
      ));
    }

    if (!registryName.getResourcePath().equals(this.path)) {

      if (debug) {
        logFile.debug(String.format(
            "%s[!!] Path mismatch: (match) %s != %s (candidate)",
            logPrefix,
            this.path,
            registryName.getResourcePath()
        ));
      }
      return false;

    } else if (debug) {
      logFile.debug(String.format(
          "%s[OK] Path match: (match) %s == %s (candidate)",
          logPrefix,
          this.path,
          registryName.getResourcePath()
      ));
    }

    int itemMeta = itemStack.getMetadata();

    if (this.meta == OreDictionary.WILDCARD_VALUE
        || this.meta == itemMeta) {

      if (debug) {
        logFile.debug(String.format("%s[OK] Meta match: (match) %d == %d (candidate)", logPrefix, this.meta, itemMeta));
      }
      return true;

    } else if (debug) {
      logFile.debug(String.format(
          "%s[!!] Meta mismatch: (match) %d != %d (candidate)",
          logPrefix,
          this.meta,
          itemMeta
      ));
    }

    for (int meta : this.metas) {

      if (meta == OreDictionary.WILDCARD_VALUE
          || meta == itemMeta) {

        if (debug) {
          logFile.debug(String.format("%s[OK] Meta match: (match) %d == %d (candidate)", logPrefix, meta, itemMeta));
        }
        return true;

      } else if (debug) {
        logFile.debug(String.format("%s[!!] Meta mismatch: (match) %d != %d (candidate)", logPrefix, meta, itemMeta));
      }
    }

    return false;
  }

  @Override
  public String toString() {

    return "ItemMatcher{" +
        "domain='" + domain + '\'' +
        ", path='" + path + '\'' +
        ", meta=" + meta +
        ", metas=" + Arrays.toString(metas) +
        '}';
  }
}
