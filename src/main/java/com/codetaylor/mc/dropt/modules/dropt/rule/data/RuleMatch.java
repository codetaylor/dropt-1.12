package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.modules.dropt.rule.BlockMatcher;
import com.codetaylor.mc.dropt.modules.dropt.rule.ItemMatcher;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;

import java.util.ArrayList;
import java.util.List;

public class RuleMatch {

  public transient List<BlockMatcher> _blocks = new ArrayList<>();
  public transient List<ItemMatcher> _items = new ArrayList<>();

  public String[] blocks = new String[0];
  public String[] items = new String[0];
  public RuleMatchHarvester harvester = new RuleMatchHarvester();
  public RuleMatchBiome biomes = new RuleMatchBiome();
  public RuleMatchDimension dimensions = new RuleMatchDimension();
  public RangeInt verticalRange = new RangeInt(0, 255);

  public boolean matches(BlockEvent.HarvestDropsEvent event) {

    return this.matchVerticalRange(event)
        && this.matchBlockState(event)
        && this.matchItem(event)
        && this.harvester.matches(event.getHarvester())
        && this.biomes.matches(event.getWorld().getBiome(event.getPos()))
        && this.dimensions.matches(event.getWorld().provider.getDimension());
  }

  private boolean matchVerticalRange(BlockEvent.HarvestDropsEvent event) {

    int y = event.getPos().getY();

    return y <= this.verticalRange.max && y >= this.verticalRange.min;
  }

  private boolean matchItem(BlockEvent.HarvestDropsEvent event) {

    if (this.items.length == 0) {
      return true;
    }

    for (ItemMatcher item : this._items) {

      for (ItemStack drop : event.getDrops()) {

        if (item.matches(drop)) {
          return true;
        }
      }
    }

    return false;
  }

  private boolean matchBlockState(BlockEvent.HarvestDropsEvent event) {

    if (this.blocks.length == 0) {
      return true;
    }

    IBlockState eventBlockState = event.getState();

    for (BlockMatcher blockMatcher : this._blocks) {

      if (blockMatcher.matches(eventBlockState)) {
        return true;
      }
    }

    return false;
  }

}
