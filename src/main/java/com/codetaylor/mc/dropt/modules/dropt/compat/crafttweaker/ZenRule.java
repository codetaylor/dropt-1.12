package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.athenaeum.integration.crafttweaker.mtlib.helpers.CTInputHelper;
import com.codetaylor.mc.dropt.api.DroptAPI;
import com.codetaylor.mc.dropt.api.api.IDroptDropBuilder;
import com.codetaylor.mc.dropt.api.api.IDroptRuleBuilder;
import com.codetaylor.mc.dropt.api.reference.EnumDropStrategy;
import com.codetaylor.mc.dropt.api.reference.EnumListType;
import com.codetaylor.mc.dropt.api.reference.EnumReplaceStrategy;
import crafttweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;

@SuppressWarnings("unused")
@ZenClass("mods.dropt.Rule")
public class ZenRule {

  private final IDroptRuleBuilder rule;

  /* package */ ZenRule() {

    this.rule = DroptAPI.rule();
  }

  @ZenMethod
  public ZenRule debug() {

    this.rule.debug();
    return this;
  }

  @ZenMethod
  public ZenRule matchBlocks(String[] blockStrings) {

    this.rule.matchBlocks(blockStrings);
    return this;
  }

  @ZenMethod
  public ZenRule matchBlocks(String type, String[] blockStrings) {

    this.rule.matchBlocks(EnumListType.valueOf(type), blockStrings);
    return this;
  }

  @ZenMethod
  public ZenRule matchDrops(IIngredient[] items) {

    this.rule.matchDrops(this.getItemStrings(items));
    return this;
  }

  @ZenMethod
  public ZenRule matchDrops(String type, IIngredient[] items) {

    this.rule.matchDrops(EnumListType.valueOf(type), this.getItemStrings(items));
    return this;
  }

  @ZenMethod
  public ZenRule matchHarvester(ZenHarvester harvester) {

    this.rule.matchHarvester(harvester.getHarvester());
    return this;
  }

  @ZenMethod
  public ZenRule matchBiomes(String[] ids) {

    this.rule.matchBiomes(ids);
    return this;
  }

  @ZenMethod
  public ZenRule matchBiomes(String type, String[] ids) {

    this.rule.matchBiomes(EnumListType.valueOf(type), ids);
    return this;
  }

  @ZenMethod
  public ZenRule matchDimensions(int[] ids) {

    this.rule.matchDimensions(ids);
    return this;
  }

  @ZenMethod
  public ZenRule matchDimensions(String type, int[] ids) {

    this.rule.matchDimensions(EnumListType.valueOf(type), ids);
    return this;
  }

  @ZenMethod
  public ZenRule matchVerticalRange(int min, int max) {

    this.rule.matchVerticalRange(min, max);
    return this;
  }

  @ZenMethod
  public ZenRule replaceStrategy(String strategy) {

    this.rule.replaceStrategy(EnumReplaceStrategy.valueOf(strategy));
    return this;
  }

  @ZenMethod
  public ZenRule dropStrategy(String strategy) {

    this.rule.dropStrategy(EnumDropStrategy.valueOf(strategy));
    return this;
  }

  @ZenMethod
  public ZenRule dropCount(ZenRange range) {

    this.rule.dropCount(range.getRandomFortuneInt());
    return this;
  }

  @ZenMethod
  public ZenRule addDrop(ZenDrop drop) {

    this.rule.addDrops(new IDroptDropBuilder[]{drop.getDrop()});
    return this;
  }

  private String[] getItemStrings(IIngredient[] items) {

    ArrayList<ItemStack> itemStacks = new ArrayList<>();

    for (int i = 0; i < items.length; i++) {
      CTInputHelper.getMatchingStacks(items[i], itemStacks);
    }

    String[] itemStrings = new String[itemStacks.size()];

    for (int i = 0; i < itemStacks.size(); i++) {
      itemStrings[i] = DroptAPI.itemString(itemStacks.get(i));
    }
    return itemStrings;
  }

  public IDroptRuleBuilder getRule() {

    return this.rule;
  }
}
