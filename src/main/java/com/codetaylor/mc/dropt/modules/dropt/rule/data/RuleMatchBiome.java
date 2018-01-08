package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.modules.dropt.rule.LogFileWrapper;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class RuleMatchBiome {

  public transient List<Biome> _biomes = new ArrayList<>();

  public EnumListType type = EnumListType.WHITELIST;
  public String[] ids = new String[0];

  public boolean matches(Biome biome, LogFileWrapper logFile, boolean debug) {

    if (this._biomes.isEmpty()) {

      if (debug) {
        logFile.debug("[OK] No biome matches defined");
      }
      return true;
    }

    if (debug) {
      logFile.debug("[--] Biome list type: " + this.type);
    }

    if (this.type == EnumListType.WHITELIST) {
      boolean result = this._biomes.contains(biome);

      if (debug) {

        if (result) {
          logFile.debug(String.format(
              "[OK] Biome whitelisted: (matches) %s contains %s (candidate)",
              this._biomes,
              biome
          ));

        } else {
          logFile.debug(String.format(
              "[!!] Biome not whitelisted: (matches) %s does not contain %s (candidate)",
              this._biomes,
              biome
          ));
        }
      }

      return result;

    } else { // blacklist

      boolean result = !this._biomes.contains(biome);

      if (debug) {

        if (result) {
          logFile.debug(String.format(
              "[OK] Biome not blacklisted: (matches) %s does not contain %s (candidate)",
              this._biomes,
              biome
          ));

        } else {
          logFile.debug(String.format(
              "[!!] Biome blacklisted: (matches) %s contains %s (candidate)",
              this._biomes,
              biome
          ));
        }
      }

      return result;
    }
  }

}
