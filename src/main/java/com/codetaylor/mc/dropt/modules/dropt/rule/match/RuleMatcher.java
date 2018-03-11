package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatch;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class RuleMatcher {

  private final BlockMatcher blockMatcher;
  private final DropMatcher dropMatcher;
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
      World world,
      EntityPlayer harvester,
      BlockPos pos,
      IBlockState blockState,
      List<ItemStack> drops,
      BlockMatcher blockMatcher,
      DropMatcher dropMatcher,
      HarvesterMatcher harvesterMatcher,
      BiomeMatcher biomeMatcher,
      DimensionMatcher dimensionMatcher
  ) {

    this.blockMatcher = blockMatcher;
    this.dropMatcher = dropMatcher;
    this.harvesterMatcher = harvesterMatcher;
    this.biomeMatcher = biomeMatcher;
    this.dimensionMatcher = dimensionMatcher;

    this.harvester = harvester;
    this.biome = world.getBiome(pos);
    this.dimension = world.provider.getDimension();
    this.posY = pos.getY();
    this.blockState = blockState;
    this.drops = drops;
  }

  public boolean matches(
      RuleMatch ruleMatch,
      HeldItemCache heldItemCache,
      DebugFileWrapper logFile,
      boolean debug
  ) {

    if (debug) {
      logFile.debug("");
    }

    boolean result = this.matchVerticalRange(ruleMatch, this.posY, logFile, debug)
        && this.blockMatcher.matches(ruleMatch.blocks, this.blockState, logFile, debug)
        && this.dropMatcher.matches(ruleMatch.drops, logFile, debug, this.drops)
        && this.harvesterMatcher.matches(ruleMatch.harvester, heldItemCache, this.harvester, logFile, debug)
        && this.biomeMatcher.matches(ruleMatch.biomes, this.biome, logFile, debug)
        && this.dimensionMatcher.matches(ruleMatch.dimensions, this.dimension, logFile, debug);

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
      DebugFileWrapper logFile,
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

}
