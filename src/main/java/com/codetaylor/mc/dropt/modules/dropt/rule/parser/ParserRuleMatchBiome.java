package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import com.codetaylor.mc.dropt.modules.dropt.rule.ILogger;
import com.codetaylor.mc.dropt.modules.dropt.rule.LogFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ParserRuleMatchBiome
    implements IRuleListParser {

  @Override
  public void parse(
      RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger, LogFileWrapper logFileWrapper
  ) {

    if (rule.match == null) {

      if (rule.debug) {
        logFileWrapper.debug("Match object not defined, skipped parsing biome match");
      }
      return;
    }

    if (rule.match.biomes == null) {

      if (rule.debug) {
        logFileWrapper.debug("Match object not defined, skipped parsing biome match");
      }
      return;
    }

    for (String id : rule.match.biomes.ids) {
      ParseResult parse;

      try {
        parse = parser.parse(id);

      } catch (MalformedRecipeItemException e) {
        logger.error("Unable to parse biome <" + id + "> in file: " + ruleList._filename, e);
        continue;
      }

      if (rule.debug) {
        logFileWrapper.debug("Parsed biome match: " + parse);
      }

      Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

      if (biome == null) {
        logger.error("Unable to find registered biome: " + parse.toString());
        continue;
      }

      if (rule.debug) {
        logFileWrapper.debug("Found registered biome: " + biome);
      }

      rule.match.biomes._biomes.add(biome);

      if (rule.debug) {
        logFileWrapper.debug("Added biome match: " + biome);
      }
    }
  }
}
