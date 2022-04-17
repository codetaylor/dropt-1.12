package com.codetaylor.mc.dropt.modules.dropt.rule.match;

import com.codetaylor.mc.dropt.api.reference.EnumHarvesterType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchHarvester;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;
import java.util.UUID;

public class HarvesterMatcher {

  private final GameStageMatcher gameStageMatcher;
  private final HeldItemMatcher heldItemMatcher;
  private final PlayerNameMatcher playerNameMatcher;

  public HarvesterMatcher(
      GameStageMatcher gameStageMatcher,
      HeldItemMatcher heldItemMatcher,
      PlayerNameMatcher playerNameMatcher
  ) {

    this.gameStageMatcher = gameStageMatcher;
    this.heldItemMatcher = heldItemMatcher;
    this.playerNameMatcher = playerNameMatcher;
  }

  public boolean matches(
      RuleMatchHarvester ruleMatchHarvester,
      HeldItemCache heldItemCache,
      @Nullable EntityPlayer harvester,
      IBlockState blockState,
      boolean isExplosion,
      DebugFileWrapper logFile,
      boolean debug
  ) {

    if (debug) {
      logFile.debug("[MATCH] [--] Harvester type: " + ruleMatchHarvester.type);
    }

    if (ruleMatchHarvester.type == EnumHarvesterType.ANY) {

      if (harvester != null) {

        return this.checkHarvesterConditions(ruleMatchHarvester, heldItemCache, harvester, blockState, logFile, debug);

      } else {

        if (debug) {
          logFile.debug("[MATCH] [OK] No harvester detected");
        }
        return true;
      }

    } else if (ruleMatchHarvester.type == EnumHarvesterType.NON_PLAYER) {

      boolean result = (harvester == null);

      if (debug) {

        if (result) {
          logFile.debug("[MATCH] [OK] Harvester is null");

        } else {
          logFile.debug("[MATCH] [!!] Harvester is not null: " + harvester);
        }
      }

      return result;

    } else if (ruleMatchHarvester.type == EnumHarvesterType.PLAYER) {

      if (harvester == null) {

        if (debug) {
          logFile.debug("[MATCH] [!!] Harvester is null");
        }
        return false;
      }

      return this.checkHarvesterConditions(ruleMatchHarvester, heldItemCache, harvester, blockState, logFile, debug);

    } else if (ruleMatchHarvester.type == EnumHarvesterType.EXPLOSION) {

      if (debug) {

        if (isExplosion) {
          logFile.debug("[MATCH] [OK] Harvester is an explosion");

        } else {
          logFile.debug("[MATCH] [!!] Harvester is not an explosion: " + harvester);
        }
      }

      return isExplosion;

    } else if (ruleMatchHarvester.type == EnumHarvesterType.REAL_PLAYER) {

      if (harvester == null) {

        if (debug) {
          logFile.debug("[MATCH] [!!] Harvester is null");
        }
        return false;
      }

      boolean realPlayer = this.isRealPlayer(harvester);

      if (debug) {

        if (realPlayer) {
          logFile.debug("[MATCH] [OK] Harvester is a real player");

        } else {
          logFile.debug("[MATCH] [!!] Harvester is not a real player: " + harvester);
        }
      }

      return realPlayer
          && this.checkHarvesterConditions(ruleMatchHarvester, heldItemCache, harvester, blockState, logFile, debug);

    } else if (ruleMatchHarvester.type == EnumHarvesterType.FAKE_PLAYER) {

      if (harvester == null) {

        if (debug) {
          logFile.debug("[MATCH] [!!] Harvester is null");
        }
        return false;
      }

      boolean fakePlayer = this.isFakePlayer(harvester);

      if (debug) {

        if (fakePlayer) {
          logFile.debug("[MATCH] [OK] Harvester is a fake player");

        } else {
          logFile.debug("[MATCH] [!!] Harvester is not a fake player: " + harvester);
        }
      }

      return fakePlayer;
    }

    return false;
  }

  /**
   * Returns true if all the player conditions match.
   *
   * @param ruleMatchHarvester
   * @param heldItemCache
   * @param harvester
   * @param blockState
   * @param logFile
   * @param debug
   * @return
   */
  private boolean checkHarvesterConditions(RuleMatchHarvester ruleMatchHarvester, HeldItemCache heldItemCache, EntityPlayer harvester, IBlockState blockState, DebugFileWrapper logFile, boolean debug) {

    if (debug) {
      logFile.debug("[MATCH] [--] Harvester detected, checking harvester: " + harvester);
    }

    boolean result = this.heldItemMatcher.matches(ruleMatchHarvester.heldItemMainHand, heldItemCache.get(harvester.getName()), blockState, harvester, logFile, debug)
        && this.heldItemMatcher.matches(ruleMatchHarvester.heldItemOffHand, harvester.getHeldItemOffhand(), blockState, harvester, logFile, debug)
        && this.playerNameMatcher.matches(ruleMatchHarvester.playerName, harvester.getName(), logFile, debug)
        && this.gameStageMatcher.matches(ruleMatchHarvester.gamestages, harvester, logFile, debug);

    if (debug) {

      if (result) {
        logFile.debug("[MATCH] [OK] Harvester matching passed");

      } else {
        logFile.debug("[MATCH] [!!] Harvester matching failed");
      }
    }

    return result;
  }

  /**
   * Makes a good attempt to suss out fake players.
   * <p>
   * From: https://github.com/McJtyMods/InControl/blob/9da5907a62635b47a4c667fa4207c551b84a19de/src/main/java/mcjty/incontrol/rules/support/GenericRuleEvaluator.java
   * <p>
   * Thank you McJty!
   *
   * @param entity the entity to test
   * @return true if the given entity is a fake player
   */
  private boolean isFakePlayer(Entity entity) {

    if (!(entity instanceof EntityPlayer)) {
      return false;
    }

    if (entity instanceof FakePlayer) {
      return true;
    }

    // If this returns false it is still possible we have a fake player. Try to find the player in the list of online players
    WorldServer worldServer = DimensionManager.getWorld(0);
    MinecraftServer minecraftServer = worldServer.getMinecraftServer();

    if (minecraftServer == null) {
      return false;
    }

    PlayerList playerList = minecraftServer.getPlayerList();
    GameProfile gameProfile = ((EntityPlayer) entity).getGameProfile();
    UUID gameProfileId = gameProfile.getId();
    EntityPlayerMP playerByUUID = playerList.getPlayerByUUID(gameProfileId);

    //noinspection ConstantConditions
    if (playerByUUID == null) {

      // The player isn't online. Then it can't be real
      return true;
    }

    // The player is in the list. But is it this player?
    return entity != playerByUUID;
  }

  private boolean isRealPlayer(Entity entity) {

    if (!(entity instanceof EntityPlayer)) {
      return false;
    }

    return !this.isFakePlayer(entity);
  }

}
