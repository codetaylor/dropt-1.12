package com.codetaylor.mc.dropt.modules.dropt.rule.parse;

import com.codetaylor.mc.athenaeum.parser.recipe.item.MalformedRecipeItemException;
import com.codetaylor.mc.athenaeum.parser.recipe.item.ParseResult;
import com.codetaylor.mc.athenaeum.parser.recipe.item.RecipeItemParser;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.ILogger;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ParserRuleMatchHarvesterHeldItemMainHand
    implements IRuleListParser {

  @Override
  public void parse(
      RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger, DebugFileWrapper debugFileWrapper
  ) {

    if (rule.match == null) {

      if (rule.debug) {
        debugFileWrapper.debug("[PARSE] Match object not defined, skipped parsing heldItemMainHand match");
      }
      return;
    }

    for (String string : rule.match.harvester.heldItemMainHand.items) {

      if (string == null) {
        logger.error("[PARSE] Null item in list");
        continue;
      }

      if ("empty".equals(string.toLowerCase())) {
        rule.match.harvester.heldItemMainHand._items.add(ItemStack.EMPTY);
        debugFileWrapper.debug("[PARSE] Parsed empty item");
        continue;
      }

      ParseResult parse;

      try {
        parse = parser.parse(string);

      } catch (MalformedRecipeItemException e) {
        logger.error("[PARSE] Unable to parse item <" + string + "> in file: " + ruleList._filename, e);
        continue;
      }

      if (rule.debug) {
        debugFileWrapper.debug("[PARSE] Parsed item match: " + parse);
      }

      Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

      if (item == null) {
        logger.error("[PARSE] Unable to find registered item: " + parse.toString());
        continue;
      }

      if (rule.debug) {
        debugFileWrapper.debug("[PARSE] Found registered item: " + item);
      }

      ItemStack itemStack = new ItemStack(item, 1, parse.getMeta());
      rule.match.harvester.heldItemMainHand._items.add(itemStack);

      if (rule.debug) {
        debugFileWrapper.debug("[PARSE] Added itemStack to match: " + itemStack);
      }
    }

    String harvestLevel = rule.match.harvester.heldItemMainHand.harvestLevel;

    if (harvestLevel != null) {
      String[] split = harvestLevel.split(";");

      if (split.length != 3) {
        logger.error("[PARSE] Invalid harvest level string: " + harvestLevel);

      } else {
        rule.match.harvester.heldItemMainHand._toolClass = split[0];

        try {
          rule.match.harvester.heldItemMainHand._minHarvestLevel = Integer.valueOf(split[1]);
          rule.match.harvester.heldItemMainHand._maxHarvestLevel = Integer.valueOf(split[2]);
        } catch (Exception e) {
          logger.error("[PARSE] Invalid harvest level string: " + harvestLevel);
        }

        if (rule.match.harvester.heldItemMainHand._minHarvestLevel < 0) {
          rule.match.harvester.heldItemMainHand._minHarvestLevel = Integer.MIN_VALUE;
        }

        if (rule.match.harvester.heldItemMainHand._maxHarvestLevel < 0) {
          rule.match.harvester.heldItemMainHand._maxHarvestLevel = Integer.MAX_VALUE;
        }
      }
    }
  }
}
