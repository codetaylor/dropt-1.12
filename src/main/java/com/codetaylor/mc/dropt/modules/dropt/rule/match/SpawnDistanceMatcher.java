package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.api.reference.EnumListType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchSpawnDistance;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import net.minecraft.util.math.BlockPos;

public class SpawnDistanceMatcher {

  public boolean matches(
      RuleMatchSpawnDistance ruleMatchSpawnDistance,
      BlockPos position,
      BlockPos spawnPoint,
      DebugFileWrapper logFile,
      boolean debug
  ) {

    int min = Math.max(ruleMatchSpawnDistance.min, 0);
    int max = ruleMatchSpawnDistance.max == -1 ? Integer.MAX_VALUE : Math.max(0, ruleMatchSpawnDistance.max);

    double diffX = spawnPoint.getX() - position.getX();
    double diffZ = spawnPoint.getZ() - position.getZ();
    double distance = Math.sqrt(diffX * diffX + diffZ * diffZ);

    if (distance <= max && distance >= min) {
      boolean isWhitelist = ruleMatchSpawnDistance.type == EnumListType.WHITELIST;

      if (debug) {

        if (isWhitelist) {
          logFile.debug("[MATCH] [OK] Distance [" + distance + "] within range [" + min + "," + max + "]");

        } else {
          logFile.debug("[MATCH] [!!] Distance [" + distance + "] outside range [" + min + "," + max + "]");
        }
      }

      return isWhitelist;

    } else {
      boolean isBlacklist = ruleMatchSpawnDistance.type == EnumListType.BLACKLIST;

      if (debug) {

        if (isBlacklist) {
          logFile.debug("[MATCH] [OK] Distance [" + distance + "] outside range [" + min + "," + max + "]");

        } else {
          logFile.debug("[MATCH] [!!] Distance [" + distance + "] inside range [" + min + "," + max + "]");
        }
      }

      return isBlacklist;
    }
  }
}