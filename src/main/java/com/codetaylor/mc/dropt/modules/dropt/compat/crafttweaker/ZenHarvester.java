package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.dropt.api.DroptAPI;
import com.codetaylor.mc.dropt.api.api.IDroptHarvesterRuleBuilder;
import com.codetaylor.mc.dropt.api.reference.EnumHarvesterGameStageType;
import com.codetaylor.mc.dropt.api.reference.EnumHarvesterType;
import com.codetaylor.mc.dropt.api.reference.EnumListType;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@SuppressWarnings("unused")
@ZenClass("mods.dropt.Harvester")
public class ZenHarvester {

  private final IDroptHarvesterRuleBuilder harvester;

  public ZenHarvester() {

    this.harvester = DroptAPI.harvester();
  }

  @ZenMethod
  public ZenHarvester type(String type) {

    this.harvester.type(EnumHarvesterType.valueOf(type));
    return this;
  }

  @ZenMethod
  public ZenHarvester mainHand(String harvestLevel) {

    this.harvester.mainHand(harvestLevel);
    return this;
  }

  @ZenMethod
  public ZenHarvester mainHand(IItemStack[] items) {

    this.harvester.mainHand(ZenDropt.getItemStrings(items));
    return this;
  }

  @ZenMethod
  public ZenHarvester mainHand(String type, IItemStack[] items) {

    this.harvester.mainHand(EnumListType.valueOf(type), ZenDropt.getItemStrings(items));
    return this;
  }

  @ZenMethod
  public ZenHarvester mainHand(String type, IItemStack[] items, String harvestLevel) {

    this.harvester.mainHand(EnumListType.valueOf(type), ZenDropt.getItemStrings(items), harvestLevel);
    return this;
  }

  @ZenMethod
  public ZenHarvester gameStages(String[] stages) {

    this.harvester.gameStages(stages);
    return this;
  }

  @ZenMethod
  public ZenHarvester gameStages(String require, String[] stages) {

    this.harvester.gameStages(EnumHarvesterGameStageType.valueOf(require), stages);
    return this;
  }

  @ZenMethod
  public ZenHarvester gameStages(String type, String require, String[] stages) {

    this.harvester.gameStages(EnumListType.valueOf(type), EnumHarvesterGameStageType.valueOf(require), stages);
    return this;
  }

  @ZenMethod
  public ZenHarvester playerName(String[] names) {

    this.harvester.playerName(names);
    return this;
  }

  @ZenMethod
  public ZenHarvester playerName(String type, String[] names) {

    this.harvester.playerName(EnumListType.valueOf(type), names);
    return this;
  }

  public IDroptHarvesterRuleBuilder getHarvester() {

    return this.harvester;
  }
}
