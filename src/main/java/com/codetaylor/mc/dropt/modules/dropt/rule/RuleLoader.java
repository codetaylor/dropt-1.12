package com.codetaylor.mc.dropt.modules.dropt.rule;

import com.codetaylor.mc.athenaeum.parser.recipe.item.RecipeItemParser;
import com.codetaylor.mc.dropt.api.event.DroptLoadRulesEvent;
import com.codetaylor.mc.dropt.modules.dropt.ModuleDroptConfig;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.ILogger;
import com.codetaylor.mc.dropt.modules.dropt.rule.parse.*;
import com.google.gson.*;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import net.minecraftforge.common.MinecraftForge;

import java.io.FileReader;
import java.lang.reflect.Field;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class RuleLoader {

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

    GsonBuilder gsonBuilder = new GsonBuilder();

    if (ModuleDroptConfig.JSON_STRICT_MODE) {
      gsonBuilder.registerTypeAdapterFactory(new ValidatorAdapterFactory());
    }

    Gson gson = gsonBuilder
        .setPrettyPrinting()
        .create();

    for (Path jsonFile : jsonFiles) {

      try {
        RuleList ruleList = gson
            .fromJson(new FileReader(jsonFile.toFile()), RuleList.class);
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

        // Parse item held in off hand strings
        new ParserRuleMatchHarvesterHeldItemOffHand().parse(parser, ruleList, rule, logger, debugFileWrapper);

        // Parse biomes
        new ParserRuleMatchBiome().parse(parser, ruleList, rule, logger, debugFileWrapper);

        // Parse drop items
        new ParserRuleDropItem().parse(parser, ruleList, rule, logger, debugFileWrapper);

        // Parse drop replacement
        new ParserRuleDropReplaceBlock().parse(parser, ruleList, rule, logger, debugFileWrapper);

        ruleIndex += 1;
        rulesParsed += 1;
      }
    }

    if (ModuleDroptConfig.ENABLE_PROFILE_LOG_OUTPUT) {
      debugFileWrapper.info(String.format("Parsed %d rules in %d ms", rulesParsed, (System.currentTimeMillis() - start)));
    }
  }

  /**
   * Workaround for strict parsing with the reflection adapter.
   * https://github.com/google/gson/issues/188#issuecomment-282746095
   */
  private static class ValidatorAdapterFactory
      implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

      // If the type adapter is a reflective type adapter, we want to modify the implementation using reflection. The
      // trick is to replace the Map object used to lookup the property name. Instead of returning null if the
      // property is not found, we throw a Json exception to terminate the deserialization.
      TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);

      // Check if the type adapter is a reflective, cause this solution only work for reflection.
      if (delegate instanceof ReflectiveTypeAdapterFactory.Adapter) {

        try {
          // Get reference to the existing boundFields.
          Field f = delegate.getClass().getDeclaredField("boundFields");
          f.setAccessible(true);
          Map boundFields = (Map) f.get(delegate);

          // Then replace it with our implementation throwing exception if the value is null.
          //noinspection unchecked
          boundFields = new LinkedHashMap(boundFields) {

            @Override
            public Object get(Object key) {

              Object value = super.get(key);

              if (value == null) {
                throw new JsonParseException("Invalid property name: " + key);
              }

              return value;
            }
          };

          // Finally, push our custom map back using reflection.
          f.set(delegate, boundFields);

        } catch (Exception e) {
          // Should never happen if the implementation doesn't change.
          throw new IllegalStateException(e);
        }

      }
      return delegate;
    }
  }
}
