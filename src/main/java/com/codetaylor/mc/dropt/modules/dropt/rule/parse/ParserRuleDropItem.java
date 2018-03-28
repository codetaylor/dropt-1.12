package com.codetaylor.mc.dropt.modules.dropt.rule.parse;

import com.codetaylor.mc.athenaeum.parser.recipe.item.MalformedRecipeItemException;
import com.codetaylor.mc.athenaeum.parser.recipe.item.ParseResult;
import com.codetaylor.mc.athenaeum.parser.recipe.item.RecipeItemParser;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.ILogger;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleDrop;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

public class ParserRuleDropItem
    implements IRuleListParser {

  @Override
  public void parse(
      RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger, DebugFileWrapper debugFileWrapper
  ) {

    int dropIndex = 0;

    for (RuleDrop drop : rule.drops) {

      if (drop.item == null) {

        if (rule.debug) {
          debugFileWrapper.debug(String.format(
              "[PARSE] Drop item object not defined in IRuleDrop at index %d, skipped parsing drop item",
              dropIndex
          ));
        }
        continue;
      }

      if (drop.item.items == null || drop.item.items.length == 0) {

        if (rule.debug) {
          debugFileWrapper.debug(String.format(
              "[PARSE] Drop item.items object not defined or empty in IRuleDrop at index %d, skipped parsing drop item",
              dropIndex
          ));
        }
        continue;
      }

      if (rule.debug) {
        debugFileWrapper.debug("[PARSE] Parsing drop items for IRuleDrop at index " + dropIndex);
      }

      for (String itemString : drop.item.items) {
        ParseResult parse;

        NBTTagCompound tag = null;
        String[] split = itemString.split("#");

        if (split.length > 1) {
          // this indicates that there is an nbt tag to parse

          String nbtString = split[1];

          if (split.length > 2) {
            // this indicates that the character # was used inside the NBT string and
            // we need to stitch this back together

            StringBuilder sb = new StringBuilder(nbtString);

            for (int i = 2; i < split.length; i++) {
              sb.append("#").append(split[i]);
            }

            nbtString = sb.toString();
          }

          try {
            tag = JsonToNBT.getTagFromJson(nbtString);

          } catch (Exception e) {
            logger.error("[PARSE] Unable to parse nbt string: " + split[1]);
            continue;
          }

          if (rule.debug) {
            debugFileWrapper.debug("[PARSE] Parsed item drop nbt: " + nbtString);
          }
        }

        try {
          parse = parser.parse(split[0]);

        } catch (MalformedRecipeItemException e) {
          logger.error("[PARSE] Unable to parse item drop <" + split[0] + "> in file: " + ruleList._filename, e);
          continue;
        }

        if (rule.debug) {
          debugFileWrapper.debug("[PARSE] Parsed item drop: " + parse);
        }

        if ("ore".equals(parse.getDomain())) {
          NonNullList<ItemStack> ores = OreDictionary.getOres(parse.getPath());

          if (ores.isEmpty()) {
            logger.warn("[PARSE] No entries found for oreDict entry <" + "ore:" + parse.getPath() + "> in file: " + ruleList._filename);

          } else if (rule.debug) {
            debugFileWrapper.debug("[PARSE] Expanding oreDict entry: " + parse);
          }

          for (ItemStack ore : ores) {

            if (ore.getItem().getHasSubtypes()) {
              ParserUtil.addSubItemsToList(ore.getItem(), drop.item._items, debugFileWrapper, rule.debug);

            } else {
              ItemStack itemStack = new ItemStack(ore.getItem(), 1, ore.getMetadata());

              if (tag != null) {
                // we have a nbt tag to apply
                itemStack.setTagCompound(tag.copy());
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
            logger.error("[PARSE] Unable to find registered item <" + parse.toString() + "> in file: " + ruleList._filename);
            continue;
          }

          if (rule.debug) {
            debugFileWrapper.debug("[PARSE] Found registered item: " + item);
          }

          if (parse.getMeta() == OreDictionary.WILDCARD_VALUE) {

            if (!item.getHasSubtypes()) {
              logger.error("[PARSE] Wildcard used for item <" + parse.toString() + ">, but item has no subtypes: " + ruleList._filename);

            } else {
              ParserUtil.addSubItemsToList(item, drop.item._items, debugFileWrapper, rule.debug);
            }

          } else {
            ItemStack itemStack = new ItemStack(item, 1, parse.getMeta());

            if (tag != null) {
              // we have a nbt tag to apply
              itemStack.setTagCompound(tag.copy());
            }

            drop.item._items.add(itemStack);

            if (rule.debug) {
              debugFileWrapper.debug("[PARSE] Added itemStack to drop: " + itemStack);
            }
          }
        }
      }

      dropIndex += 1;
    }
  }
}
