package com.codetaylor.mc.dropt.modules.dropt.events;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.rule.LogFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EventHandler {

  @SubscribeEvent
  public void onHarvestDropsEvent(BlockEvent.HarvestDropsEvent event) {

    Rule matchedRule = null;
    LogFileWrapper logFileWrapper = null;

    ruleList:
    for (RuleList ruleList : ModuleDropt.RULE_LISTS) {

      for (Rule rule : ruleList.rules) {
        boolean debug = rule.debug;

        if (debug && logFileWrapper == null) {
          logFileWrapper = new LogFileWrapper(ModuleDropt.LOG_FILE_WRITER_PROVIDER.createLogFileWriter());
        }

        if (debug) {
          logFileWrapper.debug("--------------------------------------------------------------------------------------");
          logFileWrapper.debug("[EVENT] " + event.toString());
          logFileWrapper.debug("[EVENT] BlockState: " + event.getState().toString());
          logFileWrapper.debug("[EVENT] Harvester: " + event.getHarvester());
          logFileWrapper.debug("[EVENT] Drops: " + event.getDrops());
          logFileWrapper.debug("[EVENT] Position: " + event.getPos());
          logFileWrapper.debug("[EVENT] Fortune Level: " + event.getFortuneLevel());
          logFileWrapper.debug("[EVENT] Silktouch: " + event.isSilkTouching());

          World world = event.getWorld();

          if (world != null) {

            if (world.provider != null) {
              logFileWrapper.debug("[EVENT] Dimension: " + world.provider.getDimension());
            }

            logFileWrapper.debug("[EVENT] Biome: " + world.getBiome(event.getPos()).getRegistryName());
          }
        }

        if (rule.match.matches(event, logFileWrapper, debug)) {
          matchedRule = rule;
          break ruleList;
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
          matchedRule.debug
      );
    }
  }

}
