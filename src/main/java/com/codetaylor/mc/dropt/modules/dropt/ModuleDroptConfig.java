package com.codetaylor.mc.dropt.modules.dropt;

import net.minecraftforge.common.config.Config;

@Config(modid = ModuleDropt.MOD_ID, name = ModuleDropt.MOD_ID + ".module.Dropt")
public class ModuleDroptConfig {

  @Config.Comment({
      "Set to true to enable profiling output to the log file."
  })
  public static boolean ENABLE_PROFILE_LOG_OUTPUT = false;

  @Config.Comment({
      "Set to true to inject about 100,000 unique rules to assist in profiling.",
      "Used in development to test performance."
  })
  public static boolean INJECT_PROFILING_RULES = false;

  @Config.Comment({
      "Set to false to ignore unknown fields when deserializing JSON."
  })
  public static boolean JSON_STRICT_MODE = true;

}
