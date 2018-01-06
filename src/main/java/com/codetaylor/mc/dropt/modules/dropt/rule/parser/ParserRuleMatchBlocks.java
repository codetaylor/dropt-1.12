package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import com.codetaylor.mc.dropt.modules.dropt.rule.BlockMatcher;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

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

      String[] split = string.split(",");

      ParseResult parse;

      try {
        parse = parser.parse(split[0]);

      } catch (MalformedRecipeItemException e) {
        logger.error("Unable to parse block <" + split[0] + "> in file: " + ruleList._filename, e);
        continue;
      }

      Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

      if (block == null) {
        logger.error("Unable to find registered block: " + parse.toString());
        continue;
      }

      int meta = parse.getMeta();
      int[] metas = new int[split.length];

      for (int i = 1; i < split.length; i++) {

        if ("*".equals(split[i].trim())) {
          meta = OreDictionary.WILDCARD_VALUE;
          metas = new int[0];
          break;
        }

        try {
          metas[i - 1] = Integer.valueOf(split[i].trim());

        } catch (Exception e) {
          logger.error("Unable to parse extra meta for <" + string + "> in file: " + ruleList._filename, e);
        }
      }

      rule.match._blocks.add(new BlockMatcher(parse.getDomain(), parse.getPath(), meta, metas));
    }
  }
}
