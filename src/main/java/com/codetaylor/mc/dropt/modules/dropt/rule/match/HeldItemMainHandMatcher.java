package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.EnumListType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchHarvesterHeldItemMainHand;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class HeldItemMainHandMatcher {

  public boolean matches(
      RuleMatchHarvesterHeldItemMainHand ruleMatchHarvesterHeldItemMainHand,
      ItemStack heldItemStack,
      DebugFileWrapper logFile,
      boolean debug
  ) {

    if (ruleMatchHarvesterHeldItemMainHand._items.isEmpty()) {

      if (debug) {
        logFile.debug("[MATCH] [OK] No entries in heldItemMainHand to match");
      }
      return true;
    }

    if (ruleMatchHarvesterHeldItemMainHand.type == EnumListType.WHITELIST) {
      return this.checkAsWhitelist(ruleMatchHarvesterHeldItemMainHand, heldItemStack, logFile, debug);

    } else {
      return this.checkAsBlacklist(ruleMatchHarvesterHeldItemMainHand, heldItemStack, logFile, debug);
    }

  }

  private boolean checkAsWhitelist(
      RuleMatchHarvesterHeldItemMainHand ruleMatchHarvesterHeldItemMainHand,
      ItemStack heldItemStack,
      DebugFileWrapper logFile,
      boolean debug
  ) {

    for (ItemStack itemStack : ruleMatchHarvesterHeldItemMainHand._items) {
      Item heldItem = heldItemStack.getItem();
      int metadata = heldItemStack.getMetadata();

      if (itemStack.getItem() != heldItem) {

        if (debug) {
          logFile.debug(String.format(
              "[MATCH] [!!] HeldItemMainHand item mismatch: (match) %s != %s (candidate)",
              itemStack.getItem(),
              heldItem
          ));
        }
        continue;

      } else if (debug) {
        logFile.debug(String.format(
            "[MATCH] [OK] HeldItemMainHand item match: (match) %s == %s (candidate)",
            itemStack.getItem(),
            heldItem
        ));
      }

      if ((itemStack.getMetadata() == OreDictionary.WILDCARD_VALUE) || (itemStack.getMetadata() == metadata)) {

        if (debug) {
          logFile.debug(String.format(
              "[MATCH] [OK] HeldItemMainHand meta match: (match) %d == %d (candidate)",
              itemStack.getMetadata(),
              metadata
          ));
          logFile.debug("[MATCH] [OK] Found heldItemMainHand match in whitelist");
        }
        return true;

      } else if (debug) {
        logFile.debug(String.format(
            "[MATCH] [!!] HeldItemMainHand meta mismatch: (match) %d != %d (candidate)",
            itemStack.getMetadata(),
            metadata
        ));
      }
    }

    if (debug) {
      logFile.debug("[MATCH] [!!] Unable to find heldItemMainHand match in whitelist");
    }
    return false;
  }

  private boolean checkAsBlacklist(
      RuleMatchHarvesterHeldItemMainHand ruleMatchHarvesterHeldItemMainHand,
      ItemStack heldItemStack,
      DebugFileWrapper logFile,
      boolean debug
  ) {

    for (ItemStack itemStack : ruleMatchHarvesterHeldItemMainHand._items) {
      Item heldItem = heldItemStack.getItem();
      int metadata = heldItemStack.getMetadata();

      if (itemStack.getItem() != heldItem) {

        if (debug) {
          logFile.debug(String.format(
              "[MATCH] [OK] HeldItemMainHand item mismatch: (match) %s != %s (candidate)",
              itemStack.getItem(),
              heldItem
          ));
        }
        continue;

      } else if (debug) {
        logFile.debug(String.format(
            "[MATCH] [!!] HeldItemMainHand item match: (match) %s == %s (candidate)",
            itemStack.getItem(),
            heldItem
        ));
      }

      if ((itemStack.getMetadata() == OreDictionary.WILDCARD_VALUE) || (itemStack.getMetadata() == metadata)) {

        if (debug) {
          logFile.debug(String.format(
              "[MATCH] [!!] HeldItemMainHand meta match: (match) %d == %d (candidate)",
              itemStack.getMetadata(),
              metadata
          ));
          logFile.debug("[MATCH] [!!] Found heldItemMainHand match in blacklist");
        }
        return false;

      } else if (debug) {
        logFile.debug(String.format(
            "[MATCH] [OK] HeldItemMainHand meta mismatch: (match) %d != %d (candidate)",
            itemStack.getMetadata(),
            metadata
        ));
      }
    }

    if (debug) {
      logFile.debug("[MATCH] [OK] Unable to find heldItemMainHand match in blacklist");
    }
    return true;
  }
}
