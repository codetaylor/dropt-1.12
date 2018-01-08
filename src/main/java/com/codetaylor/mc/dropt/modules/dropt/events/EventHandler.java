package com.codetaylor.mc.dropt.modules.dropt.events;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.rule.LogFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EventHandler {

  @SubscribeEvent
  public void onHarvestDropsEvent(BlockEvent.HarvestDropsEvent event) {

    Rule matchedRule = null;
    LogFileWrapper logFileWrapper = null;
    RuleList parentList = null;

    for (RuleList ruleList : ModuleDropt.RULE_LISTS) {
      parentList = ruleList;
      boolean debug = ruleList.debug;

      for (Rule rule : ruleList.rules) {
        debug = debug || rule.debug;

        if (debug && logFileWrapper == null) {
          logFileWrapper = new LogFileWrapper(ModuleDropt.LOG_FILE_WRITER_PROVIDER.createLogFileWriter());
        }

        if (debug) {
          logFileWrapper.debug("--------------------------------------------------------------------------------------");
          logFileWrapper.debug("[EVENT] " + event.toString());
          logFileWrapper.debug("[EVENT] BlockState: " + event.getState().toString());
          logFileWrapper.debug("[EVENT] Harvester: " + event.getHarvester());
          logFileWrapper.debug("[EVENT] Drops: " + event.getDrops());
        }

        if (rule.match.matches(event, logFileWrapper, debug)) {
          matchedRule = rule;
          break;
        }
      }
    }

    if (matchedRule != null) {
      List<ItemStack> drops = event.getDrops();
      boolean silkTouching = event.isSilkTouching();
      int fortuneLevel = event.getFortuneLevel();
      matchedRule.modifyDrops(
          drops,
          silkTouching,
          fortuneLevel,
          logFileWrapper,
          (matchedRule.debug || parentList.debug)
      );
    }
  }

}
