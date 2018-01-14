package com.codetaylor.mc.dropt.modules.dropt.events;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.ModuleDroptConfig;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.drop.DropModifier;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.LogFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.RuleMatcher;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.RuleMatcherFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EventHandler {

  private RuleMatcherFactory ruleMatcherFactory;
  private DropModifier dropModifier;

  public EventHandler(
      RuleMatcherFactory ruleMatcherFactory,
      DropModifier dropModifier
  ) {

    this.ruleMatcherFactory = ruleMatcherFactory;
    this.dropModifier = dropModifier;
  }

  @SubscribeEvent
  public void onHarvestDropsEvent(BlockEvent.HarvestDropsEvent event) {

    Rule matchedRule = null;
    LogFileWrapper logFileWrapper = null;
    RuleMatcher ruleMatcher = this.ruleMatcherFactory.create(event);

    long start = System.currentTimeMillis();
    int checkedRuleCount = 0;

    ruleList:
    for (RuleList ruleList : ModuleDropt.RULE_LISTS) {

      for (Rule rule : ruleList.rules) {
        boolean debug = rule.debug;
        checkedRuleCount += 1;

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

        if (ruleMatcher.matches(rule.match, logFileWrapper, debug)) {
          matchedRule = rule;
          break ruleList;
        }
      }
    }

    if (ModuleDroptConfig.ENABLE_PROFILE_LOG_OUTPUT) {

      if (logFileWrapper == null) {
        logFileWrapper = new LogFileWrapper(ModuleDropt.LOG_FILE_WRITER_PROVIDER.createLogFileWriter());
      }
      logFileWrapper.info(String.format(
          "Matched rule in %d ms, checked %d rule(s)",
          (System.currentTimeMillis() - start),
          checkedRuleCount
      ));
    }

    if (matchedRule != null) {
      start = System.currentTimeMillis();
      List<ItemStack> drops = event.getDrops();
      boolean silkTouching = event.isSilkTouching();
      int fortuneLevel = event.getFortuneLevel();
      dropModifier.modifyDrops(
          matchedRule,
          drops,
          silkTouching,
          fortuneLevel,
          logFileWrapper,
          matchedRule.debug
      );
      if (ModuleDroptConfig.ENABLE_PROFILE_LOG_OUTPUT) {

        if (logFileWrapper == null) {
          logFileWrapper = new LogFileWrapper(ModuleDropt.LOG_FILE_WRITER_PROVIDER.createLogFileWriter());
        }
        logFileWrapper.info(String.format(
            "Modified drops in %d ms",
            (System.currentTimeMillis() - start)
        ));
      }
    }
  }

}
