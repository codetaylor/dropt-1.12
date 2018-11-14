package com.codetaylor.mc.dropt.modules.dropt;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.util.Injector;
import com.codetaylor.mc.dropt.ModDropt;
import com.codetaylor.mc.dropt.api.*;
import com.codetaylor.mc.dropt.api.api.IDroptDropBuilder;
import com.codetaylor.mc.dropt.api.api.IDroptHarvesterRuleBuilder;
import com.codetaylor.mc.dropt.api.api.IDroptRuleBuilder;
import com.codetaylor.mc.dropt.modules.dropt.command.Command;
import com.codetaylor.mc.dropt.modules.dropt.events.EventHandler;
import com.codetaylor.mc.dropt.modules.dropt.rule.ProfileUtil;
import com.codetaylor.mc.dropt.modules.dropt.rule.RuleLoader;
import com.codetaylor.mc.dropt.modules.dropt.rule.RuleLocator;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.drop.DropModifier;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.LoggerWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.*;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;

public class ModuleDropt
    extends ModuleBase {

  public static final String MOD_ID = ModDropt.MOD_ID;

  public static final List<RuleList> RULE_LISTS = new ArrayList<>();
  public static final Map<IBlockState, List<Rule>> RULE_CACHE = new HashMap<>();

  public static Logger LOGGER;
  public static boolean MOD_GAMESTAGES;
  public static Path RULE_PATH;
  public static Path LOG_PATH;
  public static LogFileWriterProvider LOG_FILE_WRITER_PROVIDER;
  public static ConsoleLog CONSOLE_LOG;

  private final RuleRegistrationHandler ruleRegistrationHandler;

  public ModuleDropt() {

    super(0, MOD_ID);

    MinecraftForge.EVENT_BUS.register(
        new EventHandler(
            new RuleLocator(
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
                RULE_CACHE
            ),
            new DropModifier(),
            new HeldItemCache(
                new HashMap<>()
            ),
            new ExperienceCache(
                new HashMap<>()
            )
        )
    );

    CONSOLE_LOG = new ConsoleLog(
        new HashSet<>(),
        new LinkedHashMap<>()
    );

    this.ruleRegistrationHandler = new RuleRegistrationHandler(RULE_LISTS);
  }

  @Override
  public void onConstructionEvent(FMLConstructionEvent event) {

    super.onConstructionEvent(event);

    Injector injector = new Injector();

    injector.inject(
        DroptAPI.class,
        "SUPPLIER_RULE_BUILDER",
        (Supplier<IDroptRuleBuilder>) RuleBuilder::new
    );
    injector.inject(
        DroptAPI.class,
        "SUPPLIER_HARVESTER_RULE_BUILDER",
        (Supplier<IDroptHarvesterRuleBuilder>) HarvesterRuleBuilder::new
    );
    injector.inject(
        DroptAPI.class,
        "SUPPLIER_DROP_BUILDER",
        (Supplier<IDroptDropBuilder>) DropBuilder::new
    );
    injector.inject(
        DroptAPI.class,
        "CONSUMER_RULE",
        this.ruleRegistrationHandler
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
    DebugFileWrapper debugFileWrapper = new DebugFileWrapper(logFileWriter);
    RuleLoader.loadRuleLists(
        RULE_PATH,
        RULE_LISTS,
        new LoggerWrapper(LOGGER, logFileWriter),
        debugFileWrapper
    );
    debugFileWrapper.close();
  }

  @Override
  public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {

    super.onLoadCompleteEvent(event);

    FileWriter logFileWriter = LOG_FILE_WRITER_PROVIDER.createLogFileWriter();
    DebugFileWrapper debugFileWrapper = new DebugFileWrapper(logFileWriter);

    if (ModuleDroptConfig.INJECT_PROFILING_RULES) {
      ProfileUtil.injectRules(RULE_LISTS, debugFileWrapper);
    }

    RuleLoader.parseRuleLists(RULE_LISTS, new LoggerWrapper(LOGGER, logFileWriter), debugFileWrapper);
    Util.closeSilently(logFileWriter);
  }

  @Override
  public void onServerStartingEvent(FMLServerStartingEvent event) {

    super.onServerStartingEvent(event);

    event.registerServerCommand(new Command());
  }
}
