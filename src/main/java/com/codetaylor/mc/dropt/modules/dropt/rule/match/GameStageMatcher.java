package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.LogFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.EnumHarvesterGameStageType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchHarvesterGameStage;
import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class GameStageMatcher {

  public boolean matches(
      RuleMatchHarvesterGameStage ruleMatchHarvesterGameStage,
      @Nonnull EntityPlayer harvester,
      LogFileWrapper logFile,
      boolean debug
  ) {

    if (ruleMatchHarvesterGameStage.stages.length == 0) {

      if (debug) {
        logFile.debug("[MATCH] [OK] No game stages defined");
      }
      return true;
    }

    if (!ModuleDropt.MOD_GAMESTAGES) {

      if (debug) {
        logFile.debug("[MATCH] [!!] Game stages are defined, but the gamestages mod is missing");
      }
      return false;
    }

    if (debug) {
      logFile.debug("[MATCH] [--] GameStages type: " + ruleMatchHarvesterGameStage.type);
    }

    boolean result = false;

    if (ruleMatchHarvesterGameStage.type == EnumHarvesterGameStageType.ALL) {
      result = PlayerDataHandler.getStageData(harvester)
          .hasUnlockedAll(Arrays.asList(ruleMatchHarvesterGameStage.stages));

      if (debug) {

        if (result) {
          logFile.debug("[MATCH] [OK] Player has all required stages: " + Arrays.toString(ruleMatchHarvesterGameStage.stages));

        } else {
          logFile.debug("[MATCH] [OK] Player does not have all required stages: " + Arrays.toString(
              ruleMatchHarvesterGameStage.stages));
        }
      }

    } else if (ruleMatchHarvesterGameStage.type == EnumHarvesterGameStageType.ANY) {
      result = PlayerDataHandler.getStageData(harvester)
          .hasUnlockedAnyOf(Arrays.asList(ruleMatchHarvesterGameStage.stages));

      if (result) {
        logFile.debug("[MATCH] [OK] Player has one or more required stages: " + Arrays.toString(
            ruleMatchHarvesterGameStage.stages));

      } else {
        logFile.debug("[MATCH] [OK] Player does not any of the required stages: " + Arrays.toString(
            ruleMatchHarvesterGameStage.stages));
      }
    }

    return result;
  }

}
