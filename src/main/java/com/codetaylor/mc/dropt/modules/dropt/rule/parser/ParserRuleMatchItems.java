package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import com.codetaylor.mc.dropt.modules.dropt.rule.ItemMatcher;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

public class ParserRuleMatchItems
    implements IRuleListParser {

  @Override
  public void parse(
      RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger
  ) {

    if (rule.match == null) {
      return;
    }

    for (String string : rule.match.items) {

      String[] split = string.split(",");

      ParseResult parse;

      try {
        parse = parser.parse(split[0]);

      } catch (MalformedRecipeItemException e) {
        logger.error("Unable to parse item <" + split[0] + "> in file: " + ruleList._filename, e);
        continue;
      }

      if ("ore".equals(parse.getDomain())) {
        NonNullList<ItemStack> ores = OreDictionary.getOres(parse.getPath());

        if (ores.isEmpty()) {
          logger.warn("No ore dict entries found for: " + parse);
        }

        for (ItemStack ore : ores) {
          ResourceLocation registryName = ore.getItem().getRegistryName();

          if (registryName == null) {
            logger.warn("Missing registry name for: " + ore);
            continue;
          }

          rule.match._items.add(new ItemMatcher(
              registryName.getResourceDomain(),
              registryName.getResourcePath(),
              ore.getMetadata(),
              new int[0]
          ));
        }

      } else { // not an ore dict entry

        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

        if (item == null) {
          logger.error("Unable to find registered item: " + parse.toString());
          continue;
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
            logger.error("Unable to parse extra meta for <" + string + "> in file: " + ruleList._filename, e);
          }
        }

        rule.match._items.add(new ItemMatcher(parse.getDomain(), parse.getPath(), meta, metas));
      }
    }
  }
}
