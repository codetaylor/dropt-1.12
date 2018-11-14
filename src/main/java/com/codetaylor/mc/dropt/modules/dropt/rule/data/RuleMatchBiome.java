package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.api.reference.EnumListType;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class RuleMatchBiome {

  public transient List<Biome> _biomes = new ArrayList<>();

  public EnumListType type = EnumListType.WHITELIST;
  public String[] ids = new String[0];

}
