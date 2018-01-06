package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ParserRuleMatchBiome
    implements IRuleListParser {

  @Override
  public void parse(
      RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger
  ) {

    if (rule.match == null || rule.match.biomes == null) {
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

      Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

      if (biome == null) {
        logger.error("Unable to find registered biome: " + parse.toString());
        continue;
      }

      rule.match.biomes._biomes.add(biome);
    }
  }
}
