package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.EnumListType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchBiome;
import net.minecraft.world.biome.Biome;

public class BiomeMatcher {

  public boolean matches(RuleMatchBiome ruleMatchBiome, Biome biome, DebugFileWrapper logFile, boolean debug) {

    if (ruleMatchBiome._biomes.isEmpty()) {

      if (debug) {
        logFile.debug("[MATCH] [OK] No biome matches defined");
      }
      return true;
    }

    if (debug) {
      logFile.debug("[MATCH] [--] Biome list type: " + ruleMatchBiome.type);
    }

    if (ruleMatchBiome.type == EnumListType.WHITELIST) {
      boolean result = ruleMatchBiome._biomes.contains(biome);

      if (debug) {

        if (result) {
          logFile.debug(String.format(
              "[MATCH] [OK] Biome whitelisted: (matches) %s contains %s (candidate)",
              ruleMatchBiome._biomes,
              biome
          ));

        } else {
          logFile.debug(String.format(
              "[MATCH] [!!] Biome not whitelisted: (matches) %s does not contain %s (candidate)",
              ruleMatchBiome._biomes,
              biome
          ));
        }
      }

      return result;

    } else { // blacklist

      boolean result = !ruleMatchBiome._biomes.contains(biome);

      if (debug) {

        if (result) {
          logFile.debug(String.format(
              "[MATCH] [OK] Biome not blacklisted: (matches) %s does not contain %s (candidate)",
              ruleMatchBiome._biomes,
              biome
          ));

        } else {
          logFile.debug(String.format(
              "[MATCH] [!!] Biome blacklisted: (matches) %s contains %s (candidate)",
              ruleMatchBiome._biomes,
              biome
          ));
        }
      }

      return result;
    }
  }

}
