package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ParserRuleMatchBlocks
    implements IRuleListParser {

  @Override
  public void parse(
      RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger
  ) {

    if (rule.match == null) {
      return;
    }

    for (String string : rule.match.blocks) {
      ParseResult parse;

      try {
        parse = parser.parse(string);

      } catch (MalformedRecipeItemException e) {
        logger.error("Unable to parse block <" + string + "> in file: " + ruleList._filename, e);
        continue;
      }

      Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

      if (block == null) {
        logger.error("Unable to find registered block: " + parse.toString());
        continue;
      }

      IBlockState blockState = block.getStateFromMeta(parse.getMeta());
      rule.match._blocks.add(blockState);
    }
  }
}
