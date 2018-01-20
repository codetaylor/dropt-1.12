package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;

public class RuleDropSelector {

  public RuleDropSelectorWeight weight = new RuleDropSelectorWeight();
  public EnumSilktouch silktouch = EnumSilktouch.ANY;
  public int fortuneLevelRequired = 0;

  public boolean isValidCandidate(
      boolean isSilkTouching,
      int fortuneLevel,
      DebugFileWrapper logFile,
      boolean debug
  ) {

    if (fortuneLevel < this.fortuneLevelRequired) {

      if (debug) {
        logFile.debug(String.format(
            "[DROP] [SELECTOR] [!!] Required fortune level not met: (required) %d > %d (candidate)",
            this.fortuneLevelRequired,
            fortuneLevel
        ));
      }
      return false;

    } else if (debug) {
      logFile.debug(String.format(
          "[DROP] [SELECTOR] [OK] Required fortune level met: (required) %d <= %d (candidate)",
          this.fortuneLevelRequired,
          fortuneLevel
      ));
    }

    if (this.silktouch == EnumSilktouch.REQUIRED) {

      if (!isSilkTouching) {

        if (debug) {
          logFile.debug("[DROP] [SELECTOR] [!!] Silk touch required");
        }
        return false;

      } else if (debug) {
        logFile.debug("[DROP] [SELECTOR] [OK] Silk touch requirement met");
      }

    } else if (this.silktouch == EnumSilktouch.EXCLUDED) {

      if (isSilkTouching) {
        if (debug) {
          logFile.debug("[DROP] [SELECTOR] [!!] Must not have silk touch");
        }
        return false;

      } else if (debug) {
        logFile.debug("[DROP] [SELECTOR] [OK] No silk touch requirement met");
      }
    }

    return true;
  }
}
