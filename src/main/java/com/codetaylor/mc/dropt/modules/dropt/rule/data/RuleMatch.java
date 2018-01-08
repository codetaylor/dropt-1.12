package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.modules.dropt.rule.BlockMatcher;
import com.codetaylor.mc.dropt.modules.dropt.rule.ItemMatcher;
import com.codetaylor.mc.dropt.modules.dropt.rule.LogFileWrapper;
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

  public boolean matches(BlockEvent.HarvestDropsEvent event, LogFileWrapper logFile, boolean debug) {

    boolean result = this.matchVerticalRange(event, logFile, debug)
        && this.matchBlockState(event, logFile, debug)
        && this.matchItem(event, logFile, debug)
        && this.harvester.matches(event.getHarvester(), logFile, debug)
        && this.biomes.matches(event.getWorld().getBiome(event.getPos()), logFile, debug)
        && this.dimensions.matches(event.getWorld().provider.getDimension(), logFile, debug);

    if (debug) {

      if (result) {
        logFile.debug("[OK] Rule matched");

      } else {
        logFile.debug("[!!] Rule not matched");
      }
    }

    return result;
  }

  private boolean matchVerticalRange(BlockEvent.HarvestDropsEvent event, LogFileWrapper logFile, boolean debug) {

    int y = event.getPos().getY();
    boolean result = (y <= this.verticalRange.max) && (y >= this.verticalRange.min);

    if (debug) {

      if (!result) {
        logFile.debug(String.format(
            "[!!] Vertical position out of bounds: %d <= %d <= %d",
            this.verticalRange.min,
            y,
            this.verticalRange.max
        ));

      } else {
        logFile.debug(String.format(
            "[OK] Vertical position within bounds: %d <= %d <= %d",
            this.verticalRange.min,
            y,
            this.verticalRange.max
        ));
      }
    }

    return result;
  }

  private boolean matchItem(
      BlockEvent.HarvestDropsEvent event,
      LogFileWrapper logFile,
      boolean debug
  ) {

    if (this.items.length == 0) {

      if (debug) {
        logFile.debug("[OK] No item matches defined in rule");
      }
      return true;
    }

    for (ItemMatcher item : this._items) {

      for (ItemStack drop : event.getDrops()) {

        if (item.matches(drop, logFile, debug)) {

          if (debug) {
            logFile.debug("[OK] Item match found");
          }
          return true;
        }
      }
    }

    if (debug) {
      logFile.debug("[!!] No item match found");
    }
    return false;
  }

  private boolean matchBlockState(
      BlockEvent.HarvestDropsEvent event,
      LogFileWrapper logFile,
      boolean debug
  ) {

    if (this.blocks.length == 0) {

      if (debug) {
        logFile.debug("[OK] No block matches defined in rule");
      }
      return true;
    }

    IBlockState eventBlockState = event.getState();

    for (BlockMatcher blockMatcher : this._blocks) {

      if (blockMatcher.matches(eventBlockState, logFile, debug)) {

        if (debug) {
          logFile.debug("[OK] Block match found");
        }
        return true;
      }
    }

    if (debug) {
      logFile.debug("[!!] No block match found");
    }
    return false;
  }

}
