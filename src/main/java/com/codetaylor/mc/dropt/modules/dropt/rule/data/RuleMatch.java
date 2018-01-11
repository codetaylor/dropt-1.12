package com.codetaylor.mc.dropt.modules.dropt.rule.data;

public class RuleMatch {

  public RuleMatchBlocks blocks = new RuleMatchBlocks();
  public RuleMatchDrops drops = new RuleMatchDrops();
  public RuleMatchHarvester harvester = new RuleMatchHarvester();
  public RuleMatchBiome biomes = new RuleMatchBiome();
  public RuleMatchDimension dimensions = new RuleMatchDimension();
  public RangeInt verticalRange = new RangeInt(0, 255);

}
