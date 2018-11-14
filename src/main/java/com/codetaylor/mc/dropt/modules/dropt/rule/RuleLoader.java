package com.codetaylor.mc.dropt.modules.dropt.rule;

import com.codetaylor.mc.athenaeum.parser.recipe.item.RecipeItemParser;
import com.codetaylor.mc.dropt.api.event.DroptLoadRulesEvent;
import com.codetaylor.mc.dropt.modules.dropt.ModuleDroptConfig;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.ILogger;
import com.codetaylor.mc.dropt.modules.dropt.rule.parse.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.common.MinecraftForge;

import java.io.FileReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuleLoader {

  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public static void loadRuleLists(
      Path path,
      List<RuleList> ruleLists,
      ILogger logger,
      DebugFileWrapper debugFileWrapper
  ) {

    long start = System.currentTimeMillis();

    MinecraftForge.EVENT_BUS.post(new DroptLoadRulesEvent());

    if (ModuleDroptConfig.ENABLE_PROFILE_LOG_OUTPUT) {
      debugFileWrapper.info(String.format(
          "Loaded %d mod rule lists in %d ms",
          ruleLists.size(),
          (System.currentTimeMillis() - start)
      ));
    }

    if (!Files.exists(path)) {

      try {
        Files.createDirectories(path);
        debugFileWrapper.info("Created path: " + path);

      } catch (Exception e) {
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

    } catch (Exception e) {
      logger.error("Unable to load json files in path: " + path, e);
      return;
    }

    List<Path> jsonFiles = new ArrayList<>();

    for (Path pathFile : stream) {
      jsonFiles.add(pathFile);
      debugFileWrapper.info("Located rule file: " + path.relativize(pathFile));
    }

    start = System.currentTimeMillis();

    for (Path jsonFile : jsonFiles) {

      try {
        RuleList ruleList = GSON.fromJson(new FileReader(jsonFile.toFile()), RuleList.class);
        ruleList._filename = path.relativize(jsonFile).toString();
        ruleLists.add(ruleList);
        debugFileWrapper.info("Rule file loaded: " + ruleList._filename);

      } catch (Exception e) {
        logger.error("Unable to load rule file: " + path.relativize(jsonFile).toString(), e);
      }
    }

    Collections.sort(ruleLists);

    if (ruleLists.isEmpty()) {
      debugFileWrapper.info("No rule files loaded.");

    } else {

      if (ModuleDroptConfig.ENABLE_PROFILE_LOG_OUTPUT) {
        debugFileWrapper.info(String.format(
            "Loaded %d rule lists in %d ms",
            ruleLists.size(),
            (System.currentTimeMillis() - start)
        ));
      }
    }
  }

  public static void parseRuleLists(
      List<RuleList> ruleLists,
      ILogger logger,
      DebugFileWrapper debugFileWrapper
  ) {

    RecipeItemParser parser = new RecipeItemParser();

    long start = System.currentTimeMillis();
    int rulesParsed = 0;

    for (RuleList ruleList : ruleLists) {
      int ruleIndex = 0;

      for (Rule rule : ruleList.rules) {

        if (rule == null) {
          // This prevents an NPE crash when a trailing comma is left after the
          // last object in the Rule list.
          logger.warn("Malformed JSON caused null Rule, skipping null Rule");
          continue;
        }

        if (rule.debug) {
          debugFileWrapper.debug("--------------------------------------------------------------------------------------");
          debugFileWrapper.debug(String.format("Parsing rule %d in file %s", ruleIndex, ruleList._filename));
        }

        // Parse rule match block strings
        new ParserRuleMatchBlocks().parse(parser, ruleList, rule, logger, debugFileWrapper);

        // Parse rule match item strings
        new ParserRuleMatchItems().parse(parser, ruleList, rule, logger, debugFileWrapper);

        // Parse item held in main hand strings
        new ParserRuleMatchHarvesterHeldItemMainHand().parse(parser, ruleList, rule, logger, debugFileWrapper);

        // Parse biomes
        new ParserRuleMatchBiome().parse(parser, ruleList, rule, logger, debugFileWrapper);

        // Parse drop items
        new ParserRuleDropItem().parse(parser, ruleList, rule, logger, debugFileWrapper);

        ruleIndex += 1;
        rulesParsed += 1;
      }
    }

    if (ModuleDroptConfig.ENABLE_PROFILE_LOG_OUTPUT) {
      debugFileWrapper.info(String.format("Parsed %d rules in %d ms", rulesParsed, (System.currentTimeMillis() - start)));
    }
  }
}
