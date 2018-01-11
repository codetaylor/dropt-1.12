package com.codetaylor.mc.dropt.modules.dropt.rule.parse;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.ILogger;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.LogFileWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ParserRuleMatchHarvesterHeldItemMainHand
    implements IRuleListParser {

  @Override
  public void parse(
      RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger, LogFileWrapper logFileWrapper
  ) {

    if (rule.match == null) {

      if (rule.debug) {
        logFileWrapper.debug("[PARSE] Match object not defined, skipped parsing heldItemMainHand match");
      }
      return;
    }

    for (String string : rule.match.harvester.heldItemMainHand.items) {
      ParseResult parse;

      try {
        parse = parser.parse(string);

      } catch (MalformedRecipeItemException e) {
        logger.error("[PARSE] Unable to parse item <" + string + "> in file: " + ruleList._filename, e);
        continue;
      }

      if (rule.debug) {
        logFileWrapper.debug("[PARSE] Parsed item match: " + parse);
      }

      Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

      if (item == null) {
        logger.error("[PARSE] Unable to find registered item: " + parse.toString());
        continue;
      }

      if (rule.debug) {
        logFileWrapper.debug("[PARSE] Found registered item: " + item);
      }

      ItemStack itemStack = new ItemStack(item, 1, parse.getMeta());
      rule.match.harvester.heldItemMainHand._items.add(itemStack);

      if (rule.debug) {
        logFileWrapper.debug("[PARSE] Added itemStack to match: " + itemStack);
      }
    }
  }
}
