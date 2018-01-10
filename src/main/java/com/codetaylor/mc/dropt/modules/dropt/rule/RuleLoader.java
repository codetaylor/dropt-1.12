package com.codetaylor.mc.dropt.modules.dropt.rule;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.parser.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
      LogFileWrapper logFileWrapper
  ) {

    if (!Files.exists(path)) {

      try {
        Files.createDirectories(path);
        logFileWrapper.info("Created path: " + path);

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
      logFileWrapper.info("Located rule file: " + path.relativize(pathFile));
    }

    for (Path jsonFile : jsonFiles) {

      try {
        RuleList ruleList = GSON.fromJson(new FileReader(jsonFile.toFile()), RuleList.class);
        ruleList._filename = path.relativize(jsonFile).toString();
        ruleLists.add(ruleList);
        logFileWrapper.info("Rule file loaded: " + ruleList._filename);

      } catch (Exception e) {
        logger.error("Unable to load rule file: " + path.relativize(jsonFile).toString(), e);
      }
    }

    Collections.sort(ruleLists);

    if (ruleLists.isEmpty()) {
      logFileWrapper.info("No rule files loaded.");
    }
  }

  public static void parseRuleLists(
      List<RuleList> ruleLists,
      ILogger logger,
      LogFileWrapper logFileWrapper
  ) {

    RecipeItemParser parser = new RecipeItemParser();

    for (RuleList ruleList : ruleLists) {
      int ruleIndex = 0;

      for (Rule rule : ruleList.rules) {

        if (rule.debug) {
          logFileWrapper.debug("--------------------------------------------------------------------------------------");
          logFileWrapper.debug(String.format("Parsing rule %d in file %s", ruleIndex, ruleList._filename));
        }

        // Parse rule match block strings
        new ParserRuleMatchBlocks().parse(parser, ruleList, rule, logger, logFileWrapper);

        // Parse rule match item strings
        new ParserRuleMatchItems().parse(parser, ruleList, rule, logger, logFileWrapper);

        // Parse item held in main hand strings
        new ParserRuleMatchHarvesterHeldItemMainHand().parse(parser, ruleList, rule, logger, logFileWrapper);

        // Parse biomes
        new ParserRuleMatchBiome().parse(parser, ruleList, rule, logger, logFileWrapper);

        // Parse drop items
        new ParserRuleDropItem().parse(parser, ruleList, rule, logger, logFileWrapper);

        ruleIndex += 1;
      }
    }
  }
}
