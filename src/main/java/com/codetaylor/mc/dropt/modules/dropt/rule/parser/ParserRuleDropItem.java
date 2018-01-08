package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import com.codetaylor.mc.dropt.modules.dropt.rule.ILogger;
import com.codetaylor.mc.dropt.modules.dropt.rule.LogFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleDrop;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

public class ParserRuleDropItem
    implements IRuleListParser {

  @Override
  public void parse(
      RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger, LogFileWrapper logFileWrapper
  ) {

    int dropIndex = 0;

    for (RuleDrop drop : rule.drops) {

      if (drop.item == null) {

        if (rule.debug) {
          logFileWrapper.debug("Drop item object not defined, skipped parsing drop item");
        }
        return;
      }

      if (drop.item.items == null || drop.item.items.length == 0) {

        if (rule.debug) {
          logFileWrapper.debug("Drop item.items object not defined or empty, skipped parsing drop item");
        }
        return;
      }

      if (rule.debug) {
        logFileWrapper.debug("Parsing drop items for IRuleDrop at index " + dropIndex);
      }

      for (String itemString : drop.item.items) {
        ParseResult parse;

        try {
          parse = parser.parse(itemString);

        } catch (MalformedRecipeItemException e) {
          logger.error("Unable to parse item drop <" + itemString + "> in file: " + ruleList._filename, e);
          continue;
        }

        if (rule.debug) {
          logFileWrapper.debug("Parsed item drop: " + parse);
        }

        if ("ore".equals(parse.getDomain())) {
          NonNullList<ItemStack> ores = OreDictionary.getOres(parse.getPath());

          if (ores.isEmpty()) {
            logger.warn("No entries found for oreDict entry <" + "ore:" + parse.getPath() + "> in file: " + ruleList._filename);

          } else if (rule.debug) {
            logFileWrapper.debug("Expanding oreDict entry: " + parse);
          }

          for (ItemStack ore : ores) {

            if (ore.getItem().getHasSubtypes()) {
              ParserUtil.addSubItemsToList(ore.getItem(), drop.item._items, logFileWrapper, rule.debug);

            } else {
              ItemStack itemStack = new ItemStack(ore.getItem(), 1, ore.getMetadata());
              drop.item._items.add(itemStack);

              if (rule.debug) {
                logFileWrapper.debug("Added itemStack to drop: " + itemStack);
              }
            }
          }

        } else { // not an ore dict entry
          Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

          if (item == null) {
            logger.error("Unable to find registered item <" + parse.toString() + "> in file: " + ruleList._filename);
            continue;
          }

          if (rule.debug) {
            logFileWrapper.debug("Found registered item: " + item);
          }

          if (parse.getMeta() == OreDictionary.WILDCARD_VALUE) {

            if (!item.getHasSubtypes()) {
              logger.error("Wildcard used for item <" + parse.toString() + ">, but item has no subtypes: " + ruleList._filename);

            } else {
              ParserUtil.addSubItemsToList(item, drop.item._items, logFileWrapper, rule.debug);
            }

          } else {
            ItemStack itemStack = new ItemStack(item, 1, parse.getMeta());
            drop.item._items.add(itemStack);

            if (rule.debug) {
              logFileWrapper.debug("Added itemStack to drop: " + itemStack);
            }
          }
        }
      }

      dropIndex += 1;
    }
  }
}
