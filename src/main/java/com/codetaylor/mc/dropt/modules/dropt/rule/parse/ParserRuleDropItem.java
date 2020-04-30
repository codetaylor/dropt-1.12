package com.codetaylor.mc.dropt.modules.dropt.rule.parse;

import com.codetaylor.mc.athenaeum.parser.recipe.item.MalformedRecipeItemException;
import com.codetaylor.mc.athenaeum.parser.recipe.item.ParseResult;
import com.codetaylor.mc.athenaeum.parser.recipe.item.RecipeItemParser;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleDrop;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.ILogger;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

import java.util.ArrayList;
import java.util.List;

public class ParserRuleDropItem
    implements IRuleListParser {

  @Override
  public void parse(
      RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger, DebugFileWrapper debugFileWrapper
  ) {

    int dropIndex = 0;

    for (RuleDrop drop : rule.drops) {

      if (drop == null) {
        // This prevents an NPE crash when a trailing comma is left after the
        // last object in the RuleDrop list.
        logger.warn(String.format("[PARSE] Malformed JSON caused null RuleDrop, skipping null RuleDrop at index %d in file %s", dropIndex, ruleList._filename));
        dropIndex += 1;
        continue;
      }

      if (drop.item == null) {

        if (rule.debug) {
          debugFileWrapper.debug(String.format(
              "[PARSE] Drop item object not defined in IRuleDrop at index %d, skipped parsing drop item",
              dropIndex
          ));
        }
        dropIndex += 1;
        continue;
      }

      if (drop.item.items == null || drop.item.items.length == 0) {

        if (rule.debug) {
          debugFileWrapper.debug(String.format(
              "[PARSE] Drop item.items object not defined or empty in IRuleDrop at index %d, skipped parsing drop item",
              dropIndex
          ));
        }
        dropIndex += 1;
        continue;
      }

      if (rule.debug) {
        debugFileWrapper.debug("[PARSE] Parsing drop items for IRuleDrop at index " + dropIndex);
      }

      for (String itemString : drop.item.items) {

        ParserUtil.NBTParseResult parse;

        try {
          parse = ParserUtil.parseWithNBT(itemString, logger);

        } catch (MalformedRecipeItemException e) {
          logger.error("[PARSE] Unable to parse itemString [" + itemString + "] in file: " + ruleList._filename, e);
          continue;
        }

        if (rule.debug) {
          debugFileWrapper.debug("[PARSE] Parsed item drop: " + parse.getDomain() + ":" + parse.getPath());

          if (parse.getTag() != null) {
            debugFileWrapper.debug("[PARSE] Parsed item drop nbt: " + parse.getTag());
          }
        }

        if ("ore".equals(parse.getDomain())) {
          NonNullList<ItemStack> ores = OreDictionary.getOres(parse.getPath());

          if (ores.isEmpty()) {
            logger.warn("[PARSE] No entries found for oreDict entry [" + "ore:" + parse.getPath() + "] in file: " + ruleList._filename);

          } else if (rule.debug) {
            debugFileWrapper.debug("[PARSE] Expanding oreDict entry: " + parse);
          }

          for (ItemStack ore : ores) {

            if (ore.getItem().getHasSubtypes()) {
              ParserUtil.addSubItemsToList(ore.getItem(), drop.item._items, debugFileWrapper, rule.debug);

            } else {
              ItemStack itemStack = new ItemStack(ore.getItem(), parse.getQuantity(), ore.getMetadata());

              if (parse.getTag() != null) {
                // we have a nbt tag to apply
                itemStack.setTagCompound(parse.getTag().copy());
              }

              drop.item._items.add(itemStack);

              if (rule.debug) {
                debugFileWrapper.debug("[PARSE] Added itemStack to drop: " + itemStack);
              }
            }
          }

        } else { // not an ore dict entry
          Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

          if (item == null) {
            logger.error("[PARSE] Unable to find registered item [" + parse.toString() + "] in file: " + ruleList._filename);
            continue;
          }

          if (rule.debug) {
            debugFileWrapper.debug("[PARSE] Found registered item: " + item);
          }

          if (parse.getMeta() == OreDictionary.WILDCARD_VALUE) {

            if (!item.getHasSubtypes()) {
              logger.error("[PARSE] Wildcard used for item [" + parse.toString() + "], but item has no subtypes: " + ruleList._filename);

            } else {
              ParserUtil.addSubItemsToList(item, drop.item._items, debugFileWrapper, rule.debug);
            }

          } else {
            ItemStack itemStack = new ItemStack(item, parse.getQuantity(), parse.getMeta());

            if (parse.getTag() != null) {
              // we have a nbt tag to apply
              itemStack.setTagCompound(parse.getTag().copy());
            }

            drop.item._items.add(itemStack);

            if (rule.debug) {
              debugFileWrapper.debug("[PARSE] Added itemStack to drop: " + itemStack);
            }
          }
        }
      }

      if (drop.item.matchQuantity.drops.length > 0) {

        for (String itemString : drop.item.matchQuantity.drops) {
          ParseResult parse;

          try {
            parse = parser.parse(itemString);

          } catch (MalformedRecipeItemException e) {
            logger.error("[PARSE] Unable to parse item drop [" + itemString + "] in file: " + ruleList._filename, e);
            continue;
          }

          if ("ore".equals(parse.getDomain())) {
            drop.item.matchQuantity._drops.add(new OreIngredient(parse.getPath()));

            if (rule.debug) {
              debugFileWrapper.debug("[PARSE] Added ore ingredient to match quantity: " + parse.getPath());
            }

          } else { // not an ore dict entry
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

            if (item == null) {
              logger.error("[PARSE] Unable to find registered item [" + parse.toString() + "] in file: " + ruleList._filename);
              continue;
            }

            if (rule.debug) {
              debugFileWrapper.debug("[PARSE] Found registered item: " + item);
            }

            if (parse.getMeta() == OreDictionary.WILDCARD_VALUE) {

              if (!item.getHasSubtypes()) {
                logger.error("[PARSE] Wildcard used for item [" + parse.toString() + "], but item has no subtypes: " + ruleList._filename);

              } else {
                List<ItemStack> items = new ArrayList<>();
                ParserUtil.addSubItemsToList(item, items, debugFileWrapper, rule.debug);
                drop.item.matchQuantity._drops.add(Ingredient.fromStacks(items.toArray(new ItemStack[0])));
              }

            } else {
              ItemStack itemStack = new ItemStack(item, 1, parse.getMeta());

              drop.item.matchQuantity._drops.add(Ingredient.fromStacks(itemStack));

              if (rule.debug) {
                debugFileWrapper.debug("[PARSE] Added itemStack ingredient to match quantity: " + itemStack);
              }
            }
          }
        }

      }

      dropIndex += 1;
    }
  }
}
