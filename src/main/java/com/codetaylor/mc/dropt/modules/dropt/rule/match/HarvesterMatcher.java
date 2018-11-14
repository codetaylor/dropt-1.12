package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.api.reference.EnumHarvesterType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchHarvester;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nullable;

public class HarvesterMatcher {

  private GameStageMatcher gameStageMatcher;
  private HeldItemMainHandMatcher heldItemMainHandMatcher;
  private PlayerNameMatcher playerNameMatcher;

  public HarvesterMatcher(
      GameStageMatcher gameStageMatcher,
      HeldItemMainHandMatcher heldItemMainHandMatcher,
      PlayerNameMatcher playerNameMatcher
  ) {

    this.gameStageMatcher = gameStageMatcher;
    this.heldItemMainHandMatcher = heldItemMainHandMatcher;
    this.playerNameMatcher = playerNameMatcher;
  }

  public boolean matches(
      RuleMatchHarvester ruleMatchHarvester,
      HeldItemCache heldItemCache,
      @Nullable EntityPlayer harvester,
      IBlockState blockState,
      DebugFileWrapper logFile,
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

        boolean result = this.heldItemMainHandMatcher.matches(
            ruleMatchHarvester.heldItemMainHand,
            heldItemCache.get(harvester.getName()),
            blockState,
            harvester,
            logFile,
            debug
        )
            && this.playerNameMatcher.matches(ruleMatchHarvester.playerName, harvester.getName(), logFile, debug)
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

      boolean result = this.heldItemMainHandMatcher.matches(
          ruleMatchHarvester.heldItemMainHand,
          heldItemCache.get(harvester.getName()),
          blockState,
          harvester,
          logFile,
          debug
      )
          && this.playerNameMatcher.matches(ruleMatchHarvester.playerName, harvester.getName(), logFile, debug)
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

}
