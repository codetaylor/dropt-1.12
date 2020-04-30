package com.codetaylor.mc.dropt.modules.dropt.rule.parse;

import com.codetaylor.mc.athenaeum.parser.recipe.item.RecipeItemParser;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleDrop;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.ILogger;
import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Map;

public class ParserRuleDropReplaceBlock
    implements IRuleListParser {

  @Override
  public void parse(
      RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger, DebugFileWrapper debugFileWrapper
  ) {

    int dropIndex = 0;

    for (RuleDrop drop : rule.drops) {

      if (drop == null) {
        // This prevents an NPE crash when a trailing comma is left after the
        // last object in the RuleDrop list.
        logger.warn(String.format("[PARSE] Malformed JSON caused null RuleDrop, skipping null RuleDrop at index %d in file %s", dropIndex, ruleList._filename));
        dropIndex += 1;
        continue;
      }

      // Skip if not defined. Should never happen.

      if (drop.replaceBlock == null) {

        if (rule.debug) {
          debugFileWrapper.debug(String.format(
              "[PARSE] Drop replaceBlock object not defined in IRuleDrop at index %d, skipped parsing drop item",
              dropIndex
          ));
        }
        dropIndex += 1;
        continue;
      }

      // Parse the block if it exists.

      if (drop.replaceBlock.block == null) {

        if (rule.debug) {
          debugFileWrapper.debug(String.format(
              "[PARSE] Drop replaceBlock.block object not defined or empty in IRuleDrop at index %d, skipped parsing drop item",
              dropIndex
          ));
        }
        dropIndex += 1;
        continue;
      }

      if (rule.debug) {
        debugFileWrapper.debug("[PARSE] Parsing drop replaceBlock for IRuleDrop at index " + dropIndex);
      }

      ResourceLocation resourceLocation = new ResourceLocation(drop.replaceBlock.block);
      Block block = ForgeRegistries.BLOCKS.getValue(resourceLocation);

      if (block == null) {
        logger.error("[PARSE] Unable to parse replaceBlock.block [" + drop.replaceBlock.block + "] in file: " + ruleList._filename);
        dropIndex += 1;
        continue;
      }

      BlockStateContainer blockStateContainer = block.getBlockState();
      drop.replaceBlock._blockState = block.getDefaultState();

      // Parse the block properties if they exist.

      if (drop.replaceBlock.properties == null || drop.replaceBlock.properties.isEmpty()) {

        if (rule.debug) {
          debugFileWrapper.debug(String.format(
              "[PARSE] Drop replaceBlock.block object not defined or empty in IRuleDrop at index %d, skipped parsing drop item",
              dropIndex
          ));
        }
        dropIndex += 1;
        continue;
      }

      for (Map.Entry<String, String> entry : drop.replaceBlock.properties.entrySet()) {

        try {
          IProperty<?> property = blockStateContainer.getProperty(entry.getKey());

          if (property == null) {
            if (rule.debug) {
              debugFileWrapper.debug(String.format(
                  "[PARSE] Unable to parse block property [%s] at index %d",
                  entry.getKey(),
                  dropIndex
              ));
            }
            dropIndex += 1;
            continue;
          }

          drop.replaceBlock._blockState = this.setValue(drop.replaceBlock._blockState, property, entry.getValue(), logger, dropIndex, ruleList._filename);

        } catch (Throwable t) {
          logger.error(String.format("[PARSE] Error parsing block property [%s] for IRuleDrop at index %d in file %s", entry.getKey(), dropIndex, ruleList._filename), t);
        }
      }

      dropIndex += 1;
    }
  }

  private <T extends Comparable<T>> IBlockState setValue(IBlockState blockState, IProperty<T> property, String value, ILogger logger, int dropIndex, String filename) {

    //noinspection Guava
    Optional<T> optional = property.parseValue(value);

    if (optional.isPresent()) {
      return blockState.withProperty(property, optional.get());

    } else {
      logger.warn(String.format("[PARSE] Unable to read property %s with value %s for blockState %s for IRuleDrop at index %d in file %s", property, value, blockState.toString(), dropIndex, filename));
      return blockState;
    }
  }
}
