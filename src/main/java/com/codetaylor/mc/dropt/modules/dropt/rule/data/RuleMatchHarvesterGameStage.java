package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.rule.LogFileWrapper;
import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class RuleMatchHarvesterGameStage {

  public EnumHarvesterGameStageType type = EnumHarvesterGameStageType.ANY;
  public String[] stages = new String[0];

  public boolean matches(
      @Nonnull EntityPlayer harvester,
      LogFileWrapper logFile,
      boolean debug
  ) {

    if (this.stages.length == 0) {

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
      logFile.debug("[MATCH] [--] GameStages type: " + this.type);
    }

    boolean result = false;

    if (this.type == EnumHarvesterGameStageType.ALL) {
      result = PlayerDataHandler.getStageData(harvester).hasUnlockedAll(Arrays.asList(this.stages));

      if (debug) {

        if (result) {
          logFile.debug("[MATCH] [OK] Player has all required stages: " + Arrays.toString(this.stages));

        } else {
          logFile.debug("[MATCH] [OK] Player does not have all required stages: " + Arrays.toString(this.stages));
        }
      }

    } else if (this.type == EnumHarvesterGameStageType.ANY) {
      result = PlayerDataHandler.getStageData(harvester).hasUnlockedAnyOf(Arrays.asList(this.stages));

      if (result) {
        logFile.debug("[MATCH] [OK] Player has one or more required stages: " + Arrays.toString(this.stages));

      } else {
        logFile.debug("[MATCH] [OK] Player does not any of the required stages: " + Arrays.toString(this.stages));
      }
    }

    return result;
  }
}
