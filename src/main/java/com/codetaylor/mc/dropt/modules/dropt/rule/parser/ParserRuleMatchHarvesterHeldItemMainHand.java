package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Logger;

public class ParserRuleMatchHarvesterHeldItemMainHand
    implements IRuleListParser {

  @Override
  public void parse(
      RecipeItemParser parser, RuleList ruleList, Rule rule, Logger logger
  ) {

    if (rule.match == null) {
      return;
    }

    for (String string : rule.match.harvester.heldItemMainHand) {
      ParseResult parse;

      try {
        parse = parser.parse(string);

      } catch (MalformedRecipeItemException e) {
        logger.error("Unable to parse item <" + string + "> in file: " + ruleList._filename, e);
        continue;
      }

      Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

      if (item == null) {
        logger.error("Unable to find registered item: " + parse.toString());
        continue;
      }

      ItemStack itemStack = new ItemStack(item, 1, parse.getMeta());
      rule.match.harvester._heldItemMainHand.add(itemStack);
    }
  }
}
