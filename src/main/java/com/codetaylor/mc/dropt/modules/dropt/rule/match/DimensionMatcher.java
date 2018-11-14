package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.api.reference.EnumListType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchDimension;

import java.util.Arrays;

public class DimensionMatcher {

  public boolean matches(
      RuleMatchDimension ruleMatchDimension,
      int dimension,
      DebugFileWrapper logFile,
      boolean debug
  ) {

    if (ruleMatchDimension.ids == null || ruleMatchDimension.ids.length == 0) {

      if (debug) {
        logFile.debug("[MATCH] [OK] No dimension matches defined");
      }
      return true;
    }

    if (debug) {
      logFile.debug("[MATCH] [--] Dimension list type: " + ruleMatchDimension.type);
    }

    if (ruleMatchDimension.type == EnumListType.WHITELIST) {
      boolean result = this.contains(ruleMatchDimension.ids, dimension);

      if (debug) {

        if (result) {
          logFile.debug(String.format(
              "[MATCH] [OK] Dimension id whitelisted: (matches) %s contains %d (candidate)",
              Arrays.toString(ruleMatchDimension.ids),
              dimension
          ));

        } else {
          logFile.debug(String.format(
              "[MATCH] [!!] Dimension id not whitelisted: (matches) %s does not contain %s (candidate)",
              Arrays.toString(ruleMatchDimension.ids),
              dimension
          ));
        }
      }

      return result;

    } else { // blacklist

      boolean result = !this.contains(ruleMatchDimension.ids, dimension);

      if (debug) {

        if (result) {
          logFile.debug(String.format(
              "[MATCH] [OK] Dimension id not blacklisted: (matches) %s does not contain %s (candidate)",
              Arrays.toString(ruleMatchDimension.ids),
              dimension
          ));

        } else {
          logFile.debug(String.format(
              "[MATCH] [!!] Biome blacklisted: (matches) %s contains %s (candidate)",
              Arrays.toString(ruleMatchDimension.ids),
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
