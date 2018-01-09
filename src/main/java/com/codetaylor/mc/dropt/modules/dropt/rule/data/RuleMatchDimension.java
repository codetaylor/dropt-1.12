package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.modules.dropt.rule.LogFileWrapper;

import java.util.Arrays;

public class RuleMatchDimension {

  public EnumListType type = EnumListType.WHITELIST;
  public int[] ids = new int[0];

  public boolean matches(
      int dimension,
      LogFileWrapper logFile,
      boolean debug
  ) {

    if (this.ids == null || this.ids.length == 0) {

      if (debug) {
        logFile.debug("[MATCH] [OK] No dimension matches defined");
      }
      return true;
    }

    if (debug) {
      logFile.debug("[MATCH] [--] Dimension list type: " + this.type);
    }

    if (this.type == EnumListType.WHITELIST) {
      boolean result = this.contains(this.ids, dimension);

      if (debug) {

        if (result) {
          logFile.debug(String.format(
              "[MATCH] [OK] Dimension id whitelisted: (matches) %s contains %d (candidate)",
              Arrays.toString(this.ids),
              dimension
          ));

        } else {
          logFile.debug(String.format(
              "[MATCH] [!!] Dimension id not whitelisted: (matches) %s does not contain %s (candidate)",
              Arrays.toString(this.ids),
              dimension
          ));
        }
      }

      return result;

    } else { // blacklist

      boolean result = !this.contains(this.ids, dimension);

      if (debug) {

        if (result) {
          logFile.debug(String.format(
              "[MATCH] [OK] Dimension id not blacklisted: (matches) %s does not contain %s (candidate)",
              Arrays.toString(this.ids),
              dimension
          ));

        } else {
          logFile.debug(String.format(
              "[MATCH] [!!] Biome blacklisted: (matches) %s contains %s (candidate)",
              Arrays.toString(this.ids),
              dimension
          ));
        }
      }

      return result;
    }
  }

  private boolean contains(int[] ids, int toMatch) {

    for (int id : ids) {

      if (id == toMatch) {
        return true;
      }
    }

    return false;
  }
}
