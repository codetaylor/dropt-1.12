package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.EnumListType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchBlocks;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import net.minecraft.block.state.IBlockState;

public class BlockMatcher {

  public boolean matches(
      RuleMatchBlocks ruleMatchBlocks,
      IBlockState blockState,
      DebugFileWrapper logFile,
      boolean debug
  ) {

    if (ruleMatchBlocks.blocks.length == 0) {

      if (debug) {
        logFile.debug("[MATCH] [OK] No block matches defined in rule");
      }
      return true;
    }

    if (ruleMatchBlocks.type == EnumListType.WHITELIST) {

      for (BlockMatchEntry blockMatchEntry : ruleMatchBlocks._blocks) {

        if (blockMatchEntry.matches(blockState, logFile, debug, "[MATCH] ")) {

          if (debug) {
            logFile.debug("[MATCH] [OK] Block match found in whitelist");
          }
          return true;
        }
      }

      if (debug) {
        logFile.debug("[MATCH] [!!] No block match found in whitelist");
      }
      return false;

    } else { // blacklist

      for (BlockMatchEntry blockMatchEntry : ruleMatchBlocks._blocks) {

        if (blockMatchEntry.matches(blockState, logFile, debug, "[MATCH] ")) {

          if (debug) {
            logFile.debug("[MATCH] [!!] Block match found in blacklist");
          }
          return false;
        }
      }

      if (debug) {
        logFile.debug("[MATCH] [OK] No block match found in blacklist");
      }
      return true;
    }
  }
}
