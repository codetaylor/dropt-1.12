package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class RuleMatcherFactory {

  private final BlockMatcher blockMatcher;
  private final DropMatcher dropMatcher;
  private final HarvesterMatcher harvesterMatcher;
  private final BiomeMatcher biomeMatcher;
  private final DimensionMatcher dimensionMatcher;
  private final SpawnDistanceMatcher spawnDistanceMatcher;

  public RuleMatcherFactory(
      BlockMatcher blockMatcher,
      DropMatcher dropMatcher,
      HarvesterMatcher harvesterMatcher,
      BiomeMatcher biomeMatcher,
      DimensionMatcher dimensionMatcher,
      SpawnDistanceMatcher spawnDistanceMatcher
  ) {

    this.blockMatcher = blockMatcher;
    this.dropMatcher = dropMatcher;
    this.harvesterMatcher = harvesterMatcher;
    this.biomeMatcher = biomeMatcher;
    this.dimensionMatcher = dimensionMatcher;
    this.spawnDistanceMatcher = spawnDistanceMatcher;
  }

  public RuleMatcher create(
      World world,
      EntityPlayer harvester,
      BlockPos pos,
      IBlockState blockState,
      List<ItemStack> drops,
      boolean isExplosion
  ) {

    return new RuleMatcher(
        world,
        harvester,
        pos,
        blockState,
        drops,
        isExplosion,
        this.blockMatcher,
        this.dropMatcher,
        this.harvesterMatcher,
        this.biomeMatcher,
        this.dimensionMatcher,
        this.spawnDistanceMatcher
    );
  }

}
