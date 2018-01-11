package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import net.minecraftforge.event.world.BlockEvent;

public class RuleMatcherFactory {

  private BlockMatcher blockMatcher;
  private DropMatcher dropMatcher;
  private HarvesterMatcher harvesterMatcher;
  private BiomeMatcher biomeMatcher;
  private DimensionMatcher dimensionMatcher;

  public RuleMatcherFactory(
      BlockMatcher blockMatcher,
      DropMatcher dropMatcher, HarvesterMatcher harvesterMatcher,
      BiomeMatcher biomeMatcher,
      DimensionMatcher dimensionMatcher
  ) {

    this.blockMatcher = blockMatcher;
    this.dropMatcher = dropMatcher;
    this.harvesterMatcher = harvesterMatcher;
    this.biomeMatcher = biomeMatcher;
    this.dimensionMatcher = dimensionMatcher;
  }

  public RuleMatcher create(BlockEvent.HarvestDropsEvent event) {

    return new RuleMatcher(
        event,
        this.blockMatcher,
        this.dropMatcher,
        this.harvesterMatcher,
        this.biomeMatcher,
        this.dimensionMatcher
    );
  }

}
