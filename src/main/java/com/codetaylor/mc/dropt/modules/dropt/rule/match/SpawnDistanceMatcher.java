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

    int diffX = spawnPoint.getX() - position.getX();
    int diffZ = spawnPoint.getZ() - position.getZ();
    int distanceSq = diffX * diffX + diffZ * diffZ;

    if (distanceSq <= max * max && distanceSq >= min * min) {
      boolean result = ruleMatchSpawnDistance.type == EnumListType.WHITELIST;

      if (debug) {

        if (result) {
          logFile.debug("[MATCH] [OK] Distance [" + Math.sqrt(distanceSq) + "] within range [" + min + "," + max + "]");

        } else {
          logFile.debug("[MATCH] [!!] Distance [" + Math.sqrt(distanceSq) + "] outside range [" + min + "," + max + "]");
        }
      }

      return result;

    } else {
      boolean result = ruleMatchSpawnDistance.type == EnumListType.BLACKLIST;

      if (debug) {

        if (result) {
          logFile.debug("[MATCH] [OK] Distance [" + Math.sqrt(distanceSq) + "] outside range [" + min + "," + max + "]");

        } else {
          logFile.debug("[MATCH] [!!] Distance [" + Math.sqrt(distanceSq) + "] inside range [" + min + "," + max + "]");
        }
      }

      return result;
    }
  }
}