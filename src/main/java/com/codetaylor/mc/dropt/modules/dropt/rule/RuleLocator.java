package com.codetaylor.mc.dropt.modules.dropt.rule;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.ModuleDroptConfig;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.BlockMatcher;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.RuleMatcher;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.RuleMatcherFactory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuleLocator {

  private final RuleMatcherFactory ruleMatcherFactory;
  private final Map<IBlockState, List<Rule>> map;

  public RuleLocator(
      RuleMatcherFactory ruleMatcherFactory,
      Map<IBlockState, List<Rule>> map
  ) {

    this.ruleMatcherFactory = ruleMatcherFactory;
    this.map = map;
  }

  public Rule locate(BlockEvent.HarvestDropsEvent event) {

    IBlockState state = event.getState();

    if (state == null) {
      return null;
    }

    List<Rule> ruleList = this.map.get(state);

    if (ruleList == null) {
      // We haven't yet cached this blockState.
      ruleList = this.cacheRules(state);
      this.map.put(state, ruleList);
    }

    return this.matchRule(event, ruleList);
  }

  @Nonnull
  private List<Rule> cacheRules(IBlockState state) {

    DebugFileWrapper debugFileWrapper = null;
    BlockMatcher blockMatcher = new BlockMatcher();
    List<Rule> result = new ArrayList<>();
    int checkedRuleCount = 0;
    long start = System.currentTimeMillis();

    for (RuleList ruleList : ModuleDropt.RULE_LISTS) {

      for (Rule rule : ruleList.rules) {
        boolean debug = rule.debug;

        if (debug) {

          if (debugFileWrapper == null) {
            debugFileWrapper = new DebugFileWrapper(ModuleDropt.LOG_FILE_WRITER_PROVIDER.createLogFileWriter());
            debugFileWrapper.debug("[CACHE] Caching rules for blockState: " + state);
          }
        }

        if (blockMatcher.matches(rule.match.blocks, state, debugFileWrapper, debug)) {
          result.add(rule);
        }

        checkedRuleCount += 1;
      }
    }

    if (ModuleDroptConfig.ENABLE_PROFILE_LOG_OUTPUT) {

      if (debugFileWrapper == null) {
        debugFileWrapper = new DebugFileWrapper(ModuleDropt.LOG_FILE_WRITER_PROVIDER.createLogFileWriter());
      }
      debugFileWrapper.info(String.format(
          "Cached %d rules from %d rules in %d ms, blockState: %s",
          result.size(),
          checkedRuleCount,
          (System.currentTimeMillis() - start),
          state.toString()
      ));
    }

    if (debugFileWrapper != null) {
      debugFileWrapper.close();
    }

    return result;
  }

  private Rule matchRule(BlockEvent.HarvestDropsEvent event, List<Rule> ruleList) {

    DebugFileWrapper debugFileWrapper = null;
    RuleMatcher ruleMatcher = this.ruleMatcherFactory.create(event);

    long start = System.currentTimeMillis();
    int checkedRuleCount = 0;
    Rule matchedRule = null;

    for (Rule rule : ruleList) {
      boolean debug = rule.debug;
      checkedRuleCount += 1;

      if (debug) {

        if (debugFileWrapper == null) {
          debugFileWrapper = new DebugFileWrapper(ModuleDropt.LOG_FILE_WRITER_PROVIDER.createLogFileWriter());
        }
        this.printDebugEventInfoToFile(event, debugFileWrapper);
      }

      if (ruleMatcher.matches(rule.match, debugFileWrapper, debug)) {
        matchedRule = rule;
        break;
      }
    }

    if (ModuleDroptConfig.ENABLE_PROFILE_LOG_OUTPUT) {

      if (debugFileWrapper == null) {
        debugFileWrapper = new DebugFileWrapper(ModuleDropt.LOG_FILE_WRITER_PROVIDER.createLogFileWriter());
      }
      debugFileWrapper.info(String.format(
          "Searched %d rules in %d ms",
          checkedRuleCount,
          (System.currentTimeMillis() - start)
      ));
    }

    if (debugFileWrapper != null) {
      debugFileWrapper.close();
    }

    return matchedRule;
  }

  private void printDebugEventInfoToFile(BlockEvent.HarvestDropsEvent event, DebugFileWrapper debugFileWrapper) {

    debugFileWrapper.debug("--------------------------------------------------------------------------------------");
    debugFileWrapper.debug("[EVENT] " + event.toString());
    debugFileWrapper.debug("[EVENT] BlockState: " + event.getState().toString());
    debugFileWrapper.debug("[EVENT] Harvester: " + event.getHarvester());
    debugFileWrapper.debug("[EVENT] Drops: " + event.getDrops());
    debugFileWrapper.debug("[EVENT] Position: " + event.getPos());
    debugFileWrapper.debug("[EVENT] Fortune Level: " + event.getFortuneLevel());
    debugFileWrapper.debug("[EVENT] Silktouch: " + event.isSilkTouching());

    World world = event.getWorld();

    if (world != null) {

      if (world.provider != null) {
        debugFileWrapper.debug("[EVENT] Dimension: " + world.provider.getDimension());
      }

      debugFileWrapper.debug("[EVENT] Biome: " + world.getBiome(event.getPos()).getRegistryName());
    }
  }
}
