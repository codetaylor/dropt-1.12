package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.modules.dropt.rule.log.LogFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.EnumHarvesterType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchHarvester;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;

public class HarvesterMatcher {

  private GameStageMatcher gameStageMatcher;

  public HarvesterMatcher(GameStageMatcher gameStageMatcher) {

    this.gameStageMatcher = gameStageMatcher;
  }

  public boolean matches(
      RuleMatchHarvester ruleMatchHarvester,
      @Nullable EntityPlayer harvester,
      LogFileWrapper logFile,
      boolean debug
  ) {

    if (debug) {
      logFile.debug("[MATCH] [--] Harvester type: " + ruleMatchHarvester.type);
    }

    if (ruleMatchHarvester.type == EnumHarvesterType.ANY) {

      if (harvester != null) {

        if (debug) {
          logFile.debug("[MATCH] [--] Harvester detected, checking harvester: " + harvester);
        }

        boolean result = this.checkHeldItemMainHand(ruleMatchHarvester, harvester.getHeldItemMainhand(), logFile, debug)
            && this.checkPlayerName(ruleMatchHarvester, harvester.getName(), logFile, debug)
            && this.gameStageMatcher.matches(ruleMatchHarvester.gamestages, harvester, logFile, debug);

        if (debug) {

          if (result) {
            logFile.debug("[MATCH] [OK] Harvester matching passed");

          } else {
            logFile.debug("[MATCH] [!!] Harvester matching failed");
          }
        }

        return result;

      } else {

        if (debug) {
          logFile.debug("[MATCH] [OK] No harvester detected");
        }
        return true;
      }

    } else if (ruleMatchHarvester.type == EnumHarvesterType.NON_PLAYER) {

      boolean result = (harvester == null);

      if (debug) {

        if (result) {
          logFile.debug("[MATCH] [OK] Harvester is null");

        } else {
          logFile.debug("[MATCH] [!!] Harvester is not null: " + harvester);
        }
      }

      return result;

    } else if (ruleMatchHarvester.type == EnumHarvesterType.PLAYER) {

      if (harvester == null) {

        if (debug) {
          logFile.debug("[MATCH] [!!] Harvester is null");
        }
        return false;
      }

      if (debug) {
        logFile.debug("[MATCH] [--] Harvester detected, checking harvester: " + harvester);
      }

      boolean result = this.checkHeldItemMainHand(ruleMatchHarvester, harvester.getHeldItemMainhand(), logFile, debug)
          && this.checkPlayerName(ruleMatchHarvester, harvester.getName(), logFile, debug)
          && this.gameStageMatcher.matches(ruleMatchHarvester.gamestages, harvester, logFile, debug);

      if (debug) {

        if (result) {
          logFile.debug("[MATCH] [OK] Harvester matching passed");

        } else {
          logFile.debug("[MATCH] [!!] Harvester matching failed");
        }
      }

      return result;
    }

    return false;
  }

  private boolean checkPlayerName(
      RuleMatchHarvester ruleMatchHarvester,
      String playerName,
      LogFileWrapper logFile,
      boolean debug
  ) {

    if (ruleMatchHarvester.playerName.length == 0) {

      if (debug) {
        logFile.debug("[MATCH] [OK] No player names defined");
      }
      return true;
    }

    playerName = playerName.toLowerCase();

    for (String matchName : ruleMatchHarvester.playerName) {

      if (matchName.toLowerCase().equals(playerName)) {

        if (debug) {
          logFile.debug(String.format(
              "[MATCH] [OK] Player name match: (match) %s == %s (candidate)",
              matchName.toLowerCase(),
              playerName
          ));
        }
        return true;

      } else if (debug) {
        logFile.debug(String.format(
            "[MATCH] [!!] Player name mismatch: (match) %s != %s (candidate)",
            matchName.toLowerCase(),
            playerName
        ));
      }
    }

    logFile.debug("[MATCH] [!!] Unable to find playerName match");
    return false;
  }

  private boolean checkHeldItemMainHand(
      RuleMatchHarvester ruleMatchHarvester,
      ItemStack heldItemStack,
      LogFileWrapper logFile,
      boolean debug
  ) {

    if (ruleMatchHarvester._heldItemMainHand.isEmpty()) {

      if (debug) {
        logFile.debug("[MATCH] [OK] No entries in heldItemMainHand to match");
      }
      return true;
    }

    for (ItemStack itemStack : ruleMatchHarvester._heldItemMainHand) {
      Item heldItem = heldItemStack.getItem();
      int metadata = heldItemStack.getMetadata();

      if (itemStack.getItem() != heldItem) {

        if (debug) {
          logFile.debug(String.format(
              "[MATCH] [!!] HeldItemMainHand mismatch: (match) %s != %s (candidate)",
              itemStack.getItem(),
              heldItem
          ));
        }
        continue;

      } else if (debug) {
        logFile.debug(String.format(
            "[MATCH] [OK] HeldItemMainHand match: (match) %s == %s (candidate)",
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
      logFile.debug("[MATCH] [!!] Unable to find heldItemMainHand match");
    }
    return false;
  }

}