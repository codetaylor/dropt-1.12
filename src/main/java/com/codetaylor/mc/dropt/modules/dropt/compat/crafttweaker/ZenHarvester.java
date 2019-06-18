package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.dropt.api.DroptAPI;
import com.codetaylor.mc.dropt.api.api.IDroptHarvesterRuleBuilder;
import com.codetaylor.mc.dropt.api.reference.EnumHarvesterGameStageType;
import com.codetaylor.mc.dropt.api.reference.EnumHarvesterType;
import com.codetaylor.mc.dropt.api.reference.EnumListType;
import com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker.export.ZenDocClass;
import com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker.export.ZenDocMethod;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@SuppressWarnings("unused")
@ZenDocClass(
    value = "mods.dropt.Harvester",
    description = {
        "@see /json/syntax/#irulematchharvester"
    }
)
@ZenClass("mods.dropt.Harvester")
public class ZenHarvester {

  private final IDroptHarvesterRuleBuilder harvester;

  public ZenHarvester() {

    this.harvester = DroptAPI.harvester();
  }

  @ZenDocMethod(
      order = 1,
      description = {
          "Matches based on player / non-player / explosion.",
          "@see /json/syntax/#irulematchharvester"
      },
      args = {"type"}
  )
  @ZenMethod
  public ZenHarvester type(String type) {

    this.harvester.type(EnumHarvesterType.valueOf(type));
    return this;
  }

  @ZenDocMethod(
      order = 2,
      description = {
          "Matches based on what the player is or isn't holding.",
          "@see /json/syntax/#irulematchharvesterhelditem"
      },
      args = {"harvestLevel"}
  )
  @ZenMethod
  public ZenHarvester mainHand(String harvestLevel) {

    this.harvester.mainHand(harvestLevel);
    return this;
  }

  @ZenDocMethod(
      order = 3,
      description = {
          "Matches based on what the player is or isn't holding.",
          "@see /json/syntax/#irulematchharvesterhelditem"
      },
      args = {"items"}
  )
  @ZenMethod
  public ZenHarvester mainHand(IItemStack[] items) {

    this.harvester.mainHand(ZenDropt.getItemStrings(items));
    return this;
  }

  @ZenDocMethod(
      order = 4,
      description = {
          "Matches based on what the player is or isn't holding.",
          "@see /json/syntax/#irulematchharvesterhelditem"
      },
      args = {"type", "items"}
  )
  @ZenMethod
  public ZenHarvester mainHand(String type, IItemStack[] items) {

    this.harvester.mainHand(EnumListType.valueOf(type), ZenDropt.getItemStrings(items));
    return this;
  }

  @ZenDocMethod(
      order = 5,
      description = {
          "Matches based on what the player is or isn't holding.",
          "@see /json/syntax/#irulematchharvesterhelditem"
      },
      args = {"type", "items", "harvestLevel"}
  )
  @ZenMethod
  public ZenHarvester mainHand(String type, IItemStack[] items, String harvestLevel) {

    this.harvester.mainHand(EnumListType.valueOf(type), ZenDropt.getItemStrings(items), harvestLevel);
    return this;
  }

  @ZenDocMethod(
      order = 6,
      description = {
          "Matches based on what the player is or isn't holding.",
          "@see /json/syntax/#irulematchharvesterhelditem"
      },
      args = {"harvestLevel"}
  )
  @ZenMethod
  public ZenHarvester offHand(String harvestLevel) {

    this.harvester.offHand(harvestLevel);
    return this;
  }

  @ZenDocMethod(
      order = 7,
      description = {
          "Matches based on what the player is or isn't holding.",
          "@see /json/syntax/#irulematchharvesterhelditem"
      },
      args = {"items"}
  )
  @ZenMethod
  public ZenHarvester offHand(IItemStack[] items) {

    this.harvester.offHand(ZenDropt.getItemStrings(items));
    return this;
  }

  @ZenDocMethod(
      order = 8,
      description = {
          "Matches based on what the player is or isn't holding.",
          "@see /json/syntax/#irulematchharvesterhelditem"
      },
      args = {"type", "items"}
  )
  @ZenMethod
  public ZenHarvester offHand(String type, IItemStack[] items) {

    this.harvester.offHand(EnumListType.valueOf(type), ZenDropt.getItemStrings(items));
    return this;
  }

  @ZenDocMethod(
      order = 9,
      description = {
          "Matches based on what the player is or isn't holding.",
          "@see /json/syntax/#irulematchharvesterhelditem"
      },
      args = {"type", "items", "harvestLevel"}
  )
  @ZenMethod
  public ZenHarvester offHand(String type, IItemStack[] items, String harvestLevel) {

    this.harvester.offHand(EnumListType.valueOf(type), ZenDropt.getItemStrings(items), harvestLevel);
    return this;
  }

  @ZenDocMethod(
      order = 10,
      description = {
          "Matches based on which game stages the player has or has not unlocked.",
          "@see /json/syntax/#irulematchharvestergamestage"
      },
      args = {"stages"}
  )
  @ZenMethod
  public ZenHarvester gameStages(String[] stages) {

    this.harvester.gameStages(stages);
    return this;
  }

  @ZenDocMethod(
      order = 11,
      description = {
          "Matches based on which game stages the player has or has not unlocked.",
          "@see /json/syntax/#irulematchharvestergamestage"
      },
      args = {"require", "stages"}
  )
  @ZenMethod
  public ZenHarvester gameStages(String require, String[] stages) {

    this.harvester.gameStages(EnumHarvesterGameStageType.valueOf(require), stages);
    return this;
  }

  @ZenDocMethod(
      order = 12,
      description = {
          "Matches based on which game stages the player has or has not unlocked.",
          "@see /json/syntax/#irulematchharvestergamestage"
      },
      args = {"type", "require", "stages"}
  )
  @ZenMethod
  public ZenHarvester gameStages(String type, String require, String[] stages) {

    this.harvester.gameStages(EnumListType.valueOf(type), EnumHarvesterGameStageType.valueOf(require), stages);
    return this;
  }

  @ZenDocMethod(
      order = 13,
      description = {
          "Matches based on player name.",
          "@see /json/syntax/#irulematchharvesterplayername"
      },
      args = {"names"}
  )
  @ZenMethod
  public ZenHarvester playerName(String[] names) {

    this.harvester.playerName(names);
    return this;
  }

  @ZenDocMethod(
      order = 14,
      description = {
          "Matches based on player name.",
          "@see /json/syntax/#irulematchharvesterplayername"
      },
      args = {"type", "names"}
  )
  @ZenMethod
  public ZenHarvester playerName(String type, String[] names) {

    this.harvester.playerName(EnumListType.valueOf(type), names);
    return this;
  }

  public IDroptHarvesterRuleBuilder getHarvester() {

    return this.harvester;
  }
}
