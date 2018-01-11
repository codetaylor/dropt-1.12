package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.modules.dropt.rule.match.BlockMatchEntry;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.ItemMatchEntry;

import java.util.ArrayList;
import java.util.List;

public class RuleMatch {

  public transient List<BlockMatchEntry> _blocks = new ArrayList<>();
  public transient List<ItemMatchEntry> _items = new ArrayList<>();

  public String[] blocks = new String[0];
  public String[] items = new String[0];
  public RuleMatchHarvester harvester = new RuleMatchHarvester();
  public RuleMatchBiome biomes = new RuleMatchBiome();
  public RuleMatchDimension dimensions = new RuleMatchDimension();
  public RangeInt verticalRange = new RangeInt(0, 255);

}
