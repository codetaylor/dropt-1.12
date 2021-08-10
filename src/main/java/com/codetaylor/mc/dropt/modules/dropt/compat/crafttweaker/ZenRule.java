package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.dropt.api.DroptAPI;
import com.codetaylor.mc.dropt.api.api.IDroptDropBuilder;
import com.codetaylor.mc.dropt.api.api.IDroptRuleBuilder;
import com.codetaylor.mc.dropt.api.reference.EnumDropStrategy;
import com.codetaylor.mc.dropt.api.reference.EnumListType;
import com.codetaylor.mc.dropt.api.reference.EnumReplaceStrategy;
import com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker.export.ZenDocClass;
import com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker.export.ZenDocMethod;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@SuppressWarnings("unused")
@ZenDocClass(value = "mods.dropt.Rule", description = {
    "@see /json/syntax/#irule"
})
@ZenClass("mods.dropt.Rule")
public class ZenRule {

  private final IDroptRuleBuilder rule;

  /* package */ ZenRule() {

    this.rule = DroptAPI.rule();
  }

  @ZenDocMethod(
      order = 1,
      description = {
          "Enable logging debug output for this rule.",
          "Make sure to disable this when you're done using it. It can create a significant amount of output in the Dropt log.",
          "@see /json/syntax/#irule"
      }
  )
  @ZenMethod
  public ZenRule debug() {

    this.rule.debug();
    return this;
  }

  @ZenDocMethod(
      order = 2,
      description = {
          "Describes which blocks this rule will match.",
          "@see /json/syntax/#irulematchblocks"
      },
      args = {"blockStrings"}
  )
  @ZenMethod
  public ZenRule matchBlocks(String[] blockStrings) {

    this.rule.matchBlocks(blockStrings);
    return this;
  }

  @ZenDocMethod(
      order = 3,
      description = {
          "Describes which blocks this rule will match.",
          "@see /json/syntax/#irulematchblocks"
      },
      args = {"type", "blockStrings"}
  )
  @ZenMethod
  public ZenRule matchBlocks(String type, String[] blockStrings) {

    this.rule.matchBlocks(EnumListType.valueOf(type), blockStrings);
    return this;
  }

  @ZenDocMethod(
      order = 4,
      description = {
          "Describes which dropped items to match.",
          "@see /json/syntax/#irulematchdrops"
      },
      args = {"items"}
  )
  @ZenMethod
  public ZenRule matchDrops(IIngredient[] items) {

    this.rule.matchDrops(ZenDropt.getItemStrings(items));
    return this;
  }

  @ZenDocMethod(
      order = 5,
      description = {
          "Describes which dropped items to match.",
          "@see /json/syntax/#irulematchdrops"
      },
      args = {"type", "items"}
  )
  @ZenMethod
  public ZenRule matchDrops(String type, IIngredient[] items) {

    this.rule.matchDrops(EnumListType.valueOf(type), ZenDropt.getItemStrings(items));
    return this;
  }

  @ZenDocMethod(
      order = 6,
      description = {
          "Describes criteria about the harvester to match.",
          "@see /json/syntax/#irulematchharvester"
      },
      args = {"harvester"}
  )
  @ZenMethod
  public ZenRule matchHarvester(ZenHarvester harvester) {

    this.rule.matchHarvester(harvester.getHarvester());
    return this;
  }

  @ZenDocMethod(
      order = 7,
      description = {
          "Describes biome id's to match.",
          "@see /json/syntax/#irulematchbiome"
      },
      args = {"ids"}
  )
  @ZenMethod
  public ZenRule matchBiomes(String[] ids) {

    this.rule.matchBiomes(ids);
    return this;
  }

  @ZenDocMethod(
      order = 8,
      description = {
          "Describes biome id's to match.",
          "@see /json/syntax/#irulematchbiome"
      },
      args = {"type", "ids"}
  )
  @ZenMethod
  public ZenRule matchBiomes(String type, String[] ids) {

    this.rule.matchBiomes(EnumListType.valueOf(type), ids);
    return this;
  }

  @ZenDocMethod(
      order = 9,
      description = {
          "Describes dimension id's to match.",
          "@see /json/syntax/#irulematchdimension"
      },
      args = {"ids"}
  )
  @ZenMethod
  public ZenRule matchDimensions(int[] ids) {

    this.rule.matchDimensions(ids);
    return this;
  }

  @ZenDocMethod(
      order = 10,
      description = {
          "Describes dimension id's to match.",
          "@see /json/syntax/#irulematchdimension"
      },
      args = {"type", "ids"}
  )
  @ZenMethod
  public ZenRule matchDimensions(String type, int[] ids) {

    this.rule.matchDimensions(EnumListType.valueOf(type), ids);
    return this;
  }

  @ZenDocMethod(
      order = 11,
      description = {
          "Describes a vertical range to match.",
          "@see /json/syntax/#irulematch"
      },
      args = {"min", "max"}
  )
  @ZenMethod
  public ZenRule matchVerticalRange(int min, int max) {

    this.rule.matchVerticalRange(min, max);
    return this;
  }

  @ZenDocMethod(
      order = 12,
      description = {
          "Describes a range to match against distance from spawn.",
          "@see /json/syntax/#irulematch"
      },
      args = {"min", "max"}
  )
  @ZenMethod
  public ZenRule matchSpawnDistance(int min, int max) {

    this.rule.matchSpawnDistance(EnumListType.WHITELIST, min, max);
    return this;
  }

  @ZenDocMethod(
      order = 13,
      description = {
          "Describes a range to match against distance from spawn.",
          "@see /json/syntax/#irulematch"
      },
      args = {"type", "min", "max"}
  )
  @ZenMethod
  public ZenRule matchSpawnDistance(String type, int min, int max) {

    this.rule.matchSpawnDistance(EnumListType.valueOf(type), min, max);
    return this;
  }

  @ZenDocMethod(
      order = 14,
      description = {
          "Describes if and how the drops will be replaced.",
          "@see /json/syntax/#irule"
      },
      args = {"strategy"}
  )
  @ZenMethod
  public ZenRule replaceStrategy(String strategy) {

    this.rule.replaceStrategy(EnumReplaceStrategy.valueOf(strategy));
    return this;
  }

  @ZenDocMethod(
      order = 15,
      description = {
          "Describes how drops will be selected from the weighted picker.",
          "@see /json/syntax/#irule"
      },
      args = {"strategy"}
  )
  @ZenMethod
  public ZenRule dropStrategy(String strategy) {

    this.rule.dropStrategy(EnumDropStrategy.valueOf(strategy));
    return this;
  }

  @ZenDocMethod(
      order = 16,
      description = {
          "Describes how many times the weighted picker will be queried for drops.",
          "@see /json/syntax/#irule"
      },
      args = {"range"}
  )
  @ZenMethod
  public ZenRule dropCount(ZenRange range) {

    this.rule.dropCount(range.getRandomFortuneInt());
    return this;
  }

  @ZenDocMethod(
      order = 17,
      description = {
          "Add a drop to this rule.",
          "@see /json/syntax/#iruledrop"
      },
      args = {"drop"}
  )
  @ZenMethod
  public ZenRule addDrop(ZenDrop drop) {

    this.rule.addDrops(new IDroptDropBuilder[]{drop.getDrop()});
    return this;
  }

  @ZenDocMethod(
      order = 18,
      description = {
          "Continue matching rules after this rule if this rule is matched.",
          "@see /json/syntax/#iruledrop"
      },
      args = {"fallthrough"}
  )
  @ZenMethod
  public ZenRule fallthrough(@Optional(valueBoolean = true) boolean fallthrough) {

    this.rule.fallthrough(fallthrough);
    return this;
  }

  public IDroptRuleBuilder getRule() {

    return this.rule;
  }
}
