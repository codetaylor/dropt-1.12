package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.EnumListType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchDrops;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import net.minecraft.item.ItemStack;

import java.util.List;

public class DropMatcher {

  public boolean matches(
      RuleMatchDrops ruleMatchDrops,
      DebugFileWrapper logFile,
      boolean debug, List<ItemStack> eventDrops
  ) {

    if (ruleMatchDrops.drops.length == 0) {

      if (debug) {
        logFile.debug("[MATCH] [OK] No item matches defined in rule");
      }
      return true;
    }

    if (ruleMatchDrops.type == EnumListType.WHITELIST) {

      for (ItemMatchEntry item : ruleMatchDrops._drops) {

        for (ItemStack drop : eventDrops) {

          if (item.matches(drop, logFile, debug, "[MATCH] ")) {

            if (debug) {
              logFile.debug("[MATCH] [OK] Drop match found in whitelist");
            }
            return true;
          }
        }
      }

      if (debug) {
        logFile.debug("[MATCH] [!!] No drop match found in whitelist");
      }
      return false;

    } else { // blacklist

      for (ItemMatchEntry item : ruleMatchDrops._drops) {

        for (ItemStack drop : eventDrops) {

          if (item.matches(drop, logFile, debug, "[MATCH] ")) {

            if (debug) {
              logFile.debug("[MATCH] [!!] Drop match found in blacklist");
            }
            return false;
          }
        }
      }

      if (debug) {
        logFile.debug("[MATCH] [OK] No drop match found in blacklist");
      }
      return true;
    }
  }
}
