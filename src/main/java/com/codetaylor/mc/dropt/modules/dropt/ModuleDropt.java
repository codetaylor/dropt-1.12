package com.codetaylor.mc.dropt.modules.dropt;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.dropt.ModDropt;
import com.codetaylor.mc.dropt.modules.dropt.command.Command;
import com.codetaylor.mc.dropt.modules.dropt.events.EventHandler;
import com.codetaylor.mc.dropt.modules.dropt.rule.RuleLoader;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.parser.LoggerWrapper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModuleDropt
    extends ModuleBase {

  public static final String MOD_ID = ModDropt.MOD_ID;

  public static final List<RuleList> RULE_LISTS = new ArrayList<>();
  public static final Random RANDOM = new Random();

  public static Logger LOGGER;
  public static boolean MOD_GAMESTAGES;
  public static Path RULE_PATH;
  public static FileWriter LOG_FILE_WRITER;

  public ModuleDropt() {

    super(0, MOD_ID);

    MinecraftForge.EVENT_BUS.register(new EventHandler());
  }

  @Override
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    super.onPreInitializationEvent(event);

    MOD_GAMESTAGES = Loader.isModLoaded("gamestages");
    LOGGER = LogManager.getLogger(MOD_ID + "." + this.getClass().getSimpleName());

    File file = event.getModConfigurationDirectory();
    RULE_PATH = file.toPath().resolve(MOD_ID);

    Path logPath = RULE_PATH.resolve("dropt.log");

    if (Files.exists(logPath) && Files.isRegularFile(logPath)) {

      try {
        Files.delete(logPath);

      } catch (IOException e) {
        LOGGER.error("", e);
      }
    }

    LOG_FILE_WRITER = null;

    try {
      LOG_FILE_WRITER = new FileWriter(logPath.toFile(), true);

    } catch (IOException e) {
      LOGGER.error("", e);
    }

    RuleLoader.loadRuleLists(RULE_PATH, RULE_LISTS, new LoggerWrapper(LOGGER, LOG_FILE_WRITER));
  }

  @Override
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    super.onLoadCompleteEvent(event);

    RuleLoader.parseRuleLists(RULE_LISTS, new LoggerWrapper(LOGGER, LOG_FILE_WRITER));
  }

  @Override
  public void onServerStartingEvent(FMLServerStartingEvent event) {

    super.onServerStartingEvent(event);

    event.registerServerCommand(new Command());
  }

  @Override
  public void onServerStoppingEvent(FMLServerStoppingEvent event) {

    super.onServerStoppingEvent(event);

    try {
      LOG_FILE_WRITER.close();

    } catch (IOException e) {
      LOGGER.error("", e);
    }
  }
}
