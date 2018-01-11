package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.EnumListType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchHarvesterPlayerName;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.LogFileWrapper;

public class PlayerNameMatcher {

  public boolean matches(
      RuleMatchHarvesterPlayerName ruleMatchHarvesterPlayerName,
      String playerName,
      LogFileWrapper logFile,
      boolean debug
  ) {

    if (ruleMatchHarvesterPlayerName.playerName.length == 0) {

      if (debug) {
        logFile.debug("[MATCH] [OK] No player names defined");
      }
      return true;
    }

    playerName = playerName.toLowerCase();

    if (ruleMatchHarvesterPlayerName.type == EnumListType.WHITELIST) {

      for (String matchName : ruleMatchHarvesterPlayerName.playerName) {

        if (matchName.toLowerCase().equals(playerName)) {

          if (debug) {
            logFile.debug(String.format(
                "[MATCH] [OK] Player name match: (match) %s == %s (candidate)",
                matchName.toLowerCase(),
                playerName
            ));
            logFile.debug("[MATCH] [OK] Found playerName match in whitelist");
          }
          return true;

        } else if (debug) {
          logFile.debug(String.format(
              "[MATCH] [!!] Player name mismatch: (match) %s != %s (candidate)",
              matchName.toLowerCase(),
              playerName
          ));
        }
      }

      logFile.debug("[MATCH] [!!] Unable to find playerName match in whitelist");
      return false;

    } else { // blacklist

      for (String matchName : ruleMatchHarvesterPlayerName.playerName) {

        if (matchName.toLowerCase().equals(playerName)) {

          if (debug) {
            logFile.debug(String.format(
                "[MATCH] [!!] Player name match: (match) %s == %s (candidate)",
                matchName.toLowerCase(),
                playerName
            ));
            logFile.debug("[MATCH] [!!] Found playerName match in blacklist");
          }
          return false;

        } else if (debug) {
          logFile.debug(String.format(
              "[MATCH] [OK] Player name mismatch: (match) %s != %s (candidate)",
              matchName.toLowerCase(),
              playerName
          ));
        }
      }

      logFile.debug("[MATCH] [OK] Unable to find playerName match in blacklist");
      return true;
    }
  }
}
