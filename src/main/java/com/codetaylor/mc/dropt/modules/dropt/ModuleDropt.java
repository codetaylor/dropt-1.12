package com.codetaylor.mc.dropt.modules.dropt;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.dropt.ModDropt;
import com.codetaylor.mc.dropt.modules.dropt.command.Command;
import com.codetaylor.mc.dropt.modules.dropt.events.EventHandler;
import com.codetaylor.mc.dropt.modules.dropt.rule.ProfileUtil;
import com.codetaylor.mc.dropt.modules.dropt.rule.RuleLoader;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleDrop;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.drop.DropModifier;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.LogFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.LoggerWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ModuleDropt
    extends ModuleBase {

  public static final String MOD_ID = ModDropt.MOD_ID;

  public static final List<RuleList> RULE_LISTS = new ArrayList<>();

  public static Logger LOGGER;
  public static boolean MOD_GAMESTAGES;
  public static Path RULE_PATH;
  public static Path LOG_PATH;
  public static LogFileWriterProvider LOG_FILE_WRITER_PROVIDER;

  public ModuleDropt() {

    super(0, MOD_ID);

    MinecraftForge.EVENT_BUS.register(
        new EventHandler(
            new RuleMatcherFactory(
                new BlockMatcher(),
                new DropMatcher(),
                new HarvesterMatcher(
                    new GameStageMatcher(),
                    new HeldItemMainHandMatcher(),
                    new PlayerNameMatcher()
                ),
                new BiomeMatcher(),
                new DimensionMatcher()
            ),
            new DropModifier()
        )
    );
  }

  @Override
  public void onPreInitializationEvent(FMLPreInitializationEvent event) {

    super.onPreInitializationEvent(event);

    MOD_GAMESTAGES = Loader.isModLoaded("gamestages");
    LOGGER = LogManager.getLogger(MOD_ID + "." + this.getClass().getSimpleName());

    File configDir = event.getModConfigurationDirectory();
    RULE_PATH = configDir.toPath().resolve(MOD_ID);

    try {
      Files.createDirectories(RULE_PATH);

    } catch (IOException e) {
      LOGGER.error("", e);
    }

    LOG_PATH = configDir.toPath().resolve("../dropt.log");

    if (Files.exists(LOG_PATH) && Files.isRegularFile(LOG_PATH)) {

      try {
        Files.delete(LOG_PATH);

      } catch (IOException e) {
        LOGGER.error("", e);
      }
    }

    LOG_FILE_WRITER_PROVIDER = new LogFileWriterProvider(LOG_PATH, LOGGER);
    FileWriter logFileWriter = LOG_FILE_WRITER_PROVIDER.createLogFileWriter();
    RuleLoader.loadRuleLists(
        RULE_PATH,
        RULE_LISTS,
        new LoggerWrapper(LOGGER, logFileWriter),
        new LogFileWrapper(logFileWriter)
    );
    Util.closeSilently(logFileWriter);
  }

  @Override
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    super.onLoadCompleteEvent(event);

    FileWriter logFileWriter = LOG_FILE_WRITER_PROVIDER.createLogFileWriter();
    LogFileWrapper logFileWrapper = new LogFileWrapper(logFileWriter);

    if (ModuleDroptConfig.INJECT_PROFILING_RULES) {
      ProfileUtil.injectRules(RULE_LISTS, logFileWrapper);
    }

    RuleLoader.parseRuleLists(RULE_LISTS, new LoggerWrapper(LOGGER, logFileWriter), logFileWrapper);
    Util.closeSilently(logFileWriter);
  }

  @Override
  public void onServerStartingEvent(FMLServerStartingEvent event) {

    super.onServerStartingEvent(event);

    event.registerServerCommand(new Command());
  }
}
