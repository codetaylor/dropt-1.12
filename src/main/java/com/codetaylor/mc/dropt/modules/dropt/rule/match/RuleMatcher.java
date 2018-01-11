package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.modules.dropt.rule.log.LogFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class RuleMatcher {

  private final HarvesterMatcher harvesterMatcher;
  private final BiomeMatcher biomeMatcher;
  private final DimensionMatcher dimensionMatcher;
  private final EntityPlayer harvester;
  private final Biome biome;
  private final int dimension;
  private final int posY;
  private final IBlockState blockState;
  private final List<ItemStack> drops;

  public RuleMatcher(
      BlockEvent.HarvestDropsEvent event,
      HarvesterMatcher harvesterMatcher,
      BiomeMatcher biomeMatcher,
      DimensionMatcher dimensionMatcher
  ) {

    this.harvesterMatcher = harvesterMatcher;
    this.biomeMatcher = biomeMatcher;
    this.dimensionMatcher = dimensionMatcher;

    World world = event.getWorld();
    this.harvester = event.getHarvester();
    this.biome = world.getBiome(event.getPos());
    this.dimension = world.provider.getDimension();
    this.posY = event.getPos().getY();
    this.blockState = event.getState();
    this.drops = event.getDrops();
  }

  public boolean matches(
      RuleMatch ruleMatch,
      LogFileWrapper logFile,
      boolean debug
  ) {

    if (debug) {
      logFile.debug("");
    }

    boolean result = this.matchVerticalRange(ruleMatch, this.posY, logFile, debug)
        && this.matchBlockState(ruleMatch, this.blockState, logFile, debug)
        && this.matchItem(ruleMatch, logFile, debug, this.drops)
        && this.harvesterMatcher.matches(ruleMatch.harvester, harvester, logFile, debug)
        && this.biomeMatcher.matches(ruleMatch.biomes, biome, logFile, debug)
        && this.dimensionMatcher.matches(ruleMatch.dimensions, dimension, logFile, debug);

    if (debug) {

      if (result) {
        logFile.debug("[OK] Rule matched");

      } else {
        logFile.debug("[!!] Rule not matched");
      }
    }

    return result;
  }

  private boolean matchVerticalRange(
      RuleMatch ruleMatch,
      int posY,
      LogFileWrapper logFile,
      boolean debug
  ) {

    boolean result = (posY <= ruleMatch.verticalRange.max) && (posY >= ruleMatch.verticalRange.min);

    if (debug) {

      if (!result) {
        logFile.debug(String.format(
            "[MATCH] [!!] Vertical position out of bounds: %d <= %d <= %d",
            ruleMatch.verticalRange.min,
            posY,
            ruleMatch.verticalRange.max
        ));

      } else {
        logFile.debug(String.format(
            "[MATCH] [OK] Vertical position within bounds: %d <= %d <= %d",
            ruleMatch.verticalRange.min,
            posY,
            ruleMatch.verticalRange.max
        ));
      }
    }

    return result;
  }

  private boolean matchItem(
      RuleMatch ruleMatch,
      LogFileWrapper logFile,
      boolean debug, List<ItemStack> eventDrops
  ) {

    if (ruleMatch.items.length == 0) {

      if (debug) {
        logFile.debug("[MATCH] [OK] No item matches defined in rule");
      }
      return true;
    }

    for (ItemMatchEntry item : ruleMatch._items) {

      for (ItemStack drop : eventDrops) {

        if (item.matches(drop, logFile, debug, "[MATCH] ")) {

          if (debug) {
            logFile.debug("[MATCH] [OK] Item match found");
          }
          return true;
        }
      }
    }

    if (debug) {
      logFile.debug("[MATCH] [!!] No item match found");
    }
    return false;
  }

  private boolean matchBlockState(
      RuleMatch ruleMatch,
      IBlockState blockState,
      LogFileWrapper logFile,
      boolean debug
  ) {

    if (ruleMatch.blocks.length == 0) {

      if (debug) {
        logFile.debug("[MATCH] [OK] No block matches defined in rule");
      }
      return true;
    }

    for (BlockMatchEntry blockMatchEntry : ruleMatch._blocks) {

      if (blockMatchEntry.matches(blockState, logFile, debug, "[MATCH] ")) {

        if (debug) {
          logFile.debug("[MATCH] [OK] Block match found");
        }
        return true;
      }
    }

    if (debug) {
      logFile.debug("[MATCH] [!!] No block match found");
    }
    return false;
  }

}
