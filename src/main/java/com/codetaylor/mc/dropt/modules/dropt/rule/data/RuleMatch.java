package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.event.world.BlockEvent;

import java.util.ArrayList;
import java.util.List;

public class RuleMatch {

  public transient List<IBlockState> _blocks = new ArrayList<>();

  public String[] blocks = new String[0];
  public RuleMatchHarvester harvester = new RuleMatchHarvester();
  public RuleMatchBiome biomes = new RuleMatchBiome();
  public RuleMatchDimension dimensions = new RuleMatchDimension();

  public boolean matches(BlockEvent.HarvestDropsEvent event) {

    return this.matchBlockState(event)
        && this.harvester.matches(event.getHarvester())
        && this.biomes.matches(event.getWorld().getBiome(event.getPos()))
        && this.dimensions.matches(event.getWorld().provider.getDimension());
  }

  private boolean matchBlockState(BlockEvent.HarvestDropsEvent event) {

    boolean blockMatched = false;
    IBlockState eventBlockState = event.getState();

    for (IBlockState blockState : this._blocks) {

      if (eventBlockState == blockState) {
        blockMatched = true;
        break;
      }
    }

    return blockMatched;
  }
}
