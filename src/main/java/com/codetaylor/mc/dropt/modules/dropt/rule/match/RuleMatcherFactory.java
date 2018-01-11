package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import net.minecraftforge.event.world.BlockEvent;

public class RuleMatcherFactory {

  public RuleMatcher create(BlockEvent.HarvestDropsEvent event) {

    return new RuleMatcher(
        event,
        new HarvesterMatcher(
            new GameStageMatcher()
        ),
        new BiomeMatcher(),
        new DimensionMatcher()
    );
  }

}
