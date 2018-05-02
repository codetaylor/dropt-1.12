package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.EnumHarvesterGameStageType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.EnumListType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchHarvesterGameStage;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class GameStageMatcher {

  public boolean matches(
      RuleMatchHarvesterGameStage ruleMatchHarvesterGameStage,
      @Nonnull EntityPlayer harvester,
      DebugFileWrapper logFile,
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
      logFile.debug("[MATCH] [--] GameStages type: " + ruleMatchHarvesterGameStage.require);
    }

    boolean result = false;

    if (ruleMatchHarvesterGameStage.require == EnumHarvesterGameStageType.ALL) {
      result = GameStageHelper.hasAllOf(harvester, ruleMatchHarvesterGameStage.stages);

      if (debug) {

        if (result) {
          logFile.debug("[MATCH] [OK] Player has all required stages: " + Arrays.toString(ruleMatchHarvesterGameStage.stages));

        } else {
          logFile.debug("[MATCH] [!!] Player does not have all required stages: " + Arrays.toString(
              ruleMatchHarvesterGameStage.stages));
        }
      }

    } else if (ruleMatchHarvesterGameStage.require == EnumHarvesterGameStageType.ANY) {
      result = GameStageHelper.hasAnyOf(harvester, ruleMatchHarvesterGameStage.stages);

      if (debug) {

        if (result) {
          logFile.debug("[MATCH] [OK] Player has one or more required stages: " + Arrays.toString(
              ruleMatchHarvesterGameStage.stages));

        } else {
          logFile.debug("[MATCH] [!!] Player does not have any of the required stages: " + Arrays.toString(
              ruleMatchHarvesterGameStage.stages));
        }
      }
    }

    if (ruleMatchHarvesterGameStage.type == EnumListType.BLACKLIST) {
      result = !result;

      if (debug) {

        if (result) {
          logFile.debug("[MATCH] [OK] Gamestage result inverted due to blacklist type");

        } else {
          logFile.debug("[MATCH] [!!] Gamestage result inverted due to blacklist type");
        }
      }
    }

    return result;
  }

}
