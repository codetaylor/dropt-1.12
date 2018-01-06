package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class RuleMatchHarvesterGameStage {

  public EnumHarvesterGameStageType type = EnumHarvesterGameStageType.ANY;
  public String[] stages = new String[0];

  public boolean matches(@Nonnull EntityPlayer harvester) {

    if (this.stages.length == 0) {
      return true;
    }

    if (!ModuleDropt.MOD_GAMESTAGES) {
      return false;
    }

    if (this.type == EnumHarvesterGameStageType.ALL) {
      return PlayerDataHandler.getStageData(harvester).hasUnlockedAll(Arrays.asList(this.stages));

    } else if (this.type == EnumHarvesterGameStageType.ANY) {
      return PlayerDataHandler.getStageData(harvester).hasUnlockedAnyOf(Arrays.asList(this.stages));
    }

    return false;
  }
}
