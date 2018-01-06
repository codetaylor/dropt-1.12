package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class RuleMatchBiome {

  public transient List<Biome> _biomes = new ArrayList<>();

  public EnumListType type = EnumListType.WHITELIST;
  public String[] ids = new String[0];

  public boolean matches(Biome biome) {

    return this._biomes.isEmpty()
        || this.type == EnumListType.WHITELIST
        && this._biomes.contains(biome)
        || this.type == EnumListType.BLACKLIST
        && !this._biomes.contains(biome);
  }

}
