package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.EnumListType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchHarvesterHeldItemMainHand;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Set;

public class HeldItemMainHandMatcher {

  public boolean matches(
      RuleMatchHarvesterHeldItemMainHand ruleMatchHarvesterHeldItemMainHand,
      ItemStack heldItemStack,
      IBlockState blockState,
      EntityPlayer harvester,
      DebugFileWrapper logFile,
      boolean debug
  ) {

    Item item = heldItemStack.getItem();
    String toolClass = ruleMatchHarvesterHeldItemMainHand._toolClass;

    if (toolClass != null) {
      int harvestLevel = item.getHarvestLevel(heldItemStack, toolClass, harvester, blockState);

      // WHITELIST
      if (ruleMatchHarvesterHeldItemMainHand.type == EnumListType.WHITELIST) {
        Set<String> toolClasses = item.getToolClasses(heldItemStack);

        if (!toolClasses.contains(toolClass)) {

          if (debug) {
            logFile.debug("[MATCH] [!!] Held item doesn't have required tool class: " + toolClass);
          }
          return false;
        }

        if (debug) {
          logFile.debug("[MATCH] [OK] Held item has required tool class: " + toolClass);
        }

        if (harvestLevel < ruleMatchHarvesterHeldItemMainHand._minHarvestLevel
            || harvestLevel > ruleMatchHarvesterHeldItemMainHand._maxHarvestLevel) {

          if (debug) {
            logFile.debug("[MATCH] [!!] Harvest tool outside of level range");
          }
          return false;
        }

        if (debug) {
          logFile.debug("[MATCH] [OK] Harvest tool inside of level range");
        }

      } else { // BLACKLIST
        Set<String> toolClasses = item.getToolClasses(heldItemStack);

        if (toolClasses.contains(toolClass)) {

          if (debug) {
            logFile.debug("[MATCH] [!!] Held item has an excluded tool class: " + toolClass);
          }
          return false;
        }

        if (debug) {
          logFile.debug("[MATCH] [OK] Held item doesn't have an excluded tool class: " + toolClass);
        }

        if (harvestLevel >= ruleMatchHarvesterHeldItemMainHand._minHarvestLevel
            && harvestLevel <= ruleMatchHarvesterHeldItemMainHand._maxHarvestLevel) {

          if (debug) {
            logFile.debug("[MATCH] [!!] Harvest tool inside of level range");
          }
          return false;
        }

        if (debug) {
          logFile.debug("[MATCH] [OK] Harvest tool outside of level range");
        }
      }
    }

    return this.checkItemList(ruleMatchHarvesterHeldItemMainHand, heldItemStack, logFile, debug);
  }

  private boolean checkItemList(
      RuleMatchHarvesterHeldItemMainHand ruleMatchHarvesterHeldItemMainHand,
      ItemStack heldItemStack,
      DebugFileWrapper logFile,
      boolean debug
  ) {

    if (ruleMatchHarvesterHeldItemMainHand._items.isEmpty()) {

      if (debug) {
        logFile.debug("[MATCH] [OK] No item entries in heldItemMainHand to match");
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

    Item heldItem = heldItemStack.getItem();
    int metadata = heldItemStack.getMetadata();

    for (ItemStack itemStack : ruleMatchHarvesterHeldItemMainHand._items) {

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

    Item heldItem = heldItemStack.getItem();
    int metadata = heldItemStack.getMetadata();

    for (ItemStack itemStack : ruleMatchHarvesterHeldItemMainHand._items) {

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
