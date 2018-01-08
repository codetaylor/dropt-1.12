package com.codetaylor.mc.dropt.modules.dropt.rule;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

public class BlockMatcher {

  private String domain;
  private String path;
  private int meta;
  private int[] metas;

  public BlockMatcher(String domain, String path, int meta, int[] metas) {

    this.domain = domain;
    this.path = path;
    this.meta = meta;
    this.metas = metas;
  }

  public boolean matches(IBlockState blockState, LogFileWrapper logFile, boolean debug) {

    if (debug) {
      logFile.debug(String.format("[--] Attempting to match candidate [%s] with: [%s]", blockState, this.toString()));
    }

    ResourceLocation registryName = blockState.getBlock().getRegistryName();

    if (registryName == null) {

      if (debug) {
        logFile.debug("[!!] No registry name for match candidate: " + blockState);
      }
      return false;
    }

    if (!registryName.getResourceDomain().equals(this.domain)) {

      if (debug) {
        logFile.debug(String.format(
            "[!!] Domain mismatch: (match) %s != %s (candidate)",
            this.domain,
            registryName.getResourceDomain()
        ));
      }
      return false;

    } else if (debug) {
      logFile.debug(String.format(
          "[OK] Domain match: (match) %s == %s (candidate)",
          this.domain,
          registryName.getResourceDomain()
      ));
    }

    if (!registryName.getResourcePath().equals(this.path)) {

      if (debug) {
        logFile.debug(String.format(
            "[!!] Path mismatch: (match) %s != %s (candidate)",
            this.path,
            registryName.getResourcePath()
        ));
      }
      return false;

    } else if (debug) {
      logFile.debug(String.format(
          "[OK] Path match: (match) %s == %s (candidate)",
          this.path,
          registryName.getResourcePath()
      ));
    }

    int metaFromState = blockState.getBlock().getMetaFromState(blockState);

    if (this.meta == OreDictionary.WILDCARD_VALUE
        || this.meta == metaFromState) {

      if (debug) {
        logFile.debug(String.format("[OK] Meta match: (match) %d == %d (candidate)", this.meta, metaFromState));
      }
      return true;

    } else if (debug) {
      logFile.debug(String.format("[!!] Meta mismatch: (match) %d != %d (candidate)", this.meta, metaFromState));
    }

    for (int meta : this.metas) {

      if (meta == OreDictionary.WILDCARD_VALUE
          || meta == metaFromState) {

        if (debug) {
          logFile.debug(String.format("[OK] Meta match: (match) %d == %d (candidate)", meta, metaFromState));
        }
        return true;

      } else if (debug) {
        logFile.debug(String.format("[!!] Meta mismatch: (match) %d != %d (candidate)", meta, metaFromState));
      }
    }

    return false;
  }

  @Override
  public String toString() {

    return "BlockMatcher{" +
        "domain='" + domain + '\'' +
        ", path='" + path + '\'' +
        ", meta=" + meta +
        ", metas=" + Arrays.toString(metas) +
        '}';
  }
}
