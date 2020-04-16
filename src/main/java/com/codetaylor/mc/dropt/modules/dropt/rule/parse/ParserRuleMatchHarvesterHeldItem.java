package com.codetaylor.mc.dropt.modules.dropt.rule.parse;

import com.codetaylor.mc.athenaeum.parser.recipe.item.MalformedRecipeItemException;
import com.codetaylor.mc.athenaeum.parser.recipe.item.RecipeItemParser;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchHarvesterHeldItem;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.ILogger;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public abstract class ParserRuleMatchHarvesterHeldItem
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

    RuleMatchHarvesterHeldItem heldItemData = getHeldItemData(rule);

    for (String string : heldItemData.items) {

      if (string == null) {
        logger.error("[PARSE] Null item in list");
        continue;
      }

      if ("empty".equals(string.toLowerCase())) {
        heldItemData._items.add(ItemStack.EMPTY);
        debugFileWrapper.debug("[PARSE] Parsed empty item");
        continue;
      }

      ParserUtil.NBTParseResult parse;

      try {
        parse = ParserUtil.parseWithNBT(string, logger);

      } catch (MalformedRecipeItemException e) {
        logger.error("[PARSE] Unable to parse item [" + string + "] in file: " + ruleList._filename, e);
        continue;
      }

      if (rule.debug) {
        debugFileWrapper.debug("[PARSE] Parsed item match: " + parse);

        if (parse.getTag() != null) {
          debugFileWrapper.debug("[PARSE] Parsed item match nbt: " + parse.getTag());
        }
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

      if (parse.getTag() != null) {
        itemStack.setTagCompound(parse.getTag().copy());
      }

      heldItemData._items.add(itemStack);

      if (rule.debug) {
        debugFileWrapper.debug("[PARSE] Added itemStack to match: " + itemStack);
      }
    }

    String harvestLevel = heldItemData.harvestLevel;

    if (harvestLevel != null) {
      String[] split = harvestLevel.split(";");

      if (split.length != 3) {
        logger.error("[PARSE] Invalid harvest level string: " + harvestLevel);

      } else {
        heldItemData._toolClass = split[0];

        try {
          heldItemData._minHarvestLevel = Integer.valueOf(split[1]);
          heldItemData._maxHarvestLevel = Integer.valueOf(split[2]);
        } catch (Exception e) {
          logger.error("[PARSE] Invalid harvest level string: " + harvestLevel);
        }

        if (heldItemData._minHarvestLevel < 0) {
          heldItemData._minHarvestLevel = Integer.MIN_VALUE;
        }

        if (heldItemData._maxHarvestLevel < 0) {
          heldItemData._maxHarvestLevel = Integer.MAX_VALUE;
        }
      }
    }
  }

  protected abstract RuleMatchHarvesterHeldItem getHeldItemData(Rule rule);
}
