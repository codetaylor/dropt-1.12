package com.codetaylor.mc.dropt.modules.dropt.rule;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleDrop;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuleLoader {

  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public static void loadRuleLists(String modId, File file, List<RuleList> ruleLists, Logger logger) {

    Path path = file.toPath().resolve(modId);

    if (!Files.exists(path)) {

      try {
        Files.createDirectories(path);

      } catch (IOException e) {
        logger.error("Unable to create path: " + path, e);
        return;
      }
    }

    if (!Files.isDirectory(path)) {
      logger.error("Not a directory: " + path);
      return;
    }

    DirectoryStream<Path> stream;

    try {
      stream = Files.newDirectoryStream(
          path,
          entry -> Files.isRegularFile(entry) && entry.toFile().getName().endsWith(".json")
      );

    } catch (IOException e) {
      logger.error("Unable to load json files in path: " + path, e);
      return;
    }

    List<Path> jsonFiles = new ArrayList<>();

    for (Path pathFile : stream) {
      jsonFiles.add(pathFile);
    }

    for (Path jsonFile : jsonFiles) {

      try {
        RuleList ruleList = GSON.fromJson(new FileReader(jsonFile.toFile()), RuleList.class);
        ruleList._filename = jsonFile.toString();
        ruleLists.add(ruleList);

      } catch (FileNotFoundException e) {
        logger.error("Unable to load json file: " + jsonFile, e);
      }
    }

    Collections.sort(ruleLists);
  }

  public static void parseRuleLists(List<RuleList> ruleLists, Logger logger) {

    RecipeItemParser parser = new RecipeItemParser();

    for (RuleList ruleList : ruleLists) {

      for (Rule rule : ruleList.rules) {

        // Parse block strings
        RuleLoader.parseBlockStrings(parser, ruleList, rule, logger);

        // Parse item held in main hand strings
        RuleLoader.parseItemHeldInMainHandStrings(parser, ruleList, rule, logger);

        // Parse biomes
        RuleLoader.parseBiomeStrings(parser, ruleList, rule, logger);

        // Parse drop items
        RuleLoader.parseDropItemString(parser, ruleList, rule, logger);
      }
    }
  }

  private static void parseDropItemString(RecipeItemParser parser, RuleList ruleList, Rule rule, Logger logger) {

    for (RuleDrop drop : rule.drops) {

      if (drop.item == null || drop.item.item == null) {
        return;
      }

      ParseResult parse;

      try {
        parse = parser.parse(drop.item.item);

      } catch (MalformedRecipeItemException e) {
        logger.error("Unable to parse item drop <" + drop.item.item + "> in file: " + ruleList._filename, e);
        continue;
      }

      Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

      if (item == null) {
        logger.error("Unable to find registered item: " + parse.toString());
        continue;
      }

      drop.item._item = new ItemStack(item, 1, parse.getMeta());
    }
  }

  private static void parseBiomeStrings(RecipeItemParser parser, RuleList ruleList, Rule rule, Logger logger) {

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

  private static void parseItemHeldInMainHandStrings(
      RecipeItemParser parser,
      RuleList ruleList,
      Rule rule,
      Logger logger
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

  private static void parseBlockStrings(RecipeItemParser parser, RuleList ruleList, Rule rule, Logger logger) {

    if (rule.match == null) {
      return;
    }

    for (String string : rule.match.blocks) {
      ParseResult parse;

      try {
        parse = parser.parse(string);

      } catch (MalformedRecipeItemException e) {
        logger.error("Unable to parse block <" + string + "> in file: " + ruleList._filename, e);
        continue;
      }

      Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(parse.getDomain(), parse.getPath()));

      if (block == null) {
        logger.error("Unable to find registered block: " + parse.toString());
        continue;
      }

      IBlockState blockState = block.getStateFromMeta(parse.getMeta());
      rule.match._blocks.add(blockState);
    }
  }
}
