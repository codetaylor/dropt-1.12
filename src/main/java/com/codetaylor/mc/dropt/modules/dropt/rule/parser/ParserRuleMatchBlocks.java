package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import com.codetaylor.mc.dropt.modules.dropt.rule.BlockMatcher;
import com.codetaylor.mc.dropt.modules.dropt.rule.ILogger;
import com.codetaylor.mc.dropt.modules.dropt.rule.LogFileWrapper;
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
      RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger, LogFileWrapper logFileWrapper
  ) {

    if (rule.match == null) {

      if (rule.debug) {
        logFileWrapper.debug("[PARSE] Match object not defined, skipped parsing block match");
      }
      return;
    }

    if (rule.match.blocks == null || rule.match.blocks.length == 0) {

      if (rule.debug) {
        logFileWrapper.debug("[PARSE] No block matches defined, skipped parsing block match");
      }
      return;
    }

    for (String string : rule.match.blocks) {

      String[] split = string.split(",");

      ParseResult parse;

      try {
        parse = parser.parse(split[0]);

      } catch (MalformedRecipeItemException e) {
        logger.error("[PARSE] Unable to parse block <" + split[0] + "> in file: " + ruleList._filename, e);
        continue;
      }

      if (rule.debug) {
        logFileWrapper.debug("[PARSE] Parsed block match: " + parse);
      }

      Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

      if (block == null) {
        logger.error("[PARSE] Unable to find registered block: " + parse.toString());
        continue;
      }

      if (rule.debug) {
        logFileWrapper.debug("[PARSE] Found registered block: " + block);
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
          logger.error("[PARSE] Unable to parse extra meta for <" + string + "> in file: " + ruleList._filename, e);
        }
      }

      BlockMatcher blockMatcher = new BlockMatcher(parse.getDomain(), parse.getPath(), meta, metas);
      rule.match._blocks.add(blockMatcher);

      if (rule.debug) {
        logFileWrapper.debug("[PARSE] Added block matcher: " + blockMatcher);
      }
    }
  }
}
