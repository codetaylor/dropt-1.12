package com.codetaylor.mc.dropt.api;

import com.codetaylor.mc.athenaeum.util.ArrayHelper;
import com.codetaylor.mc.dropt.api.api.IDroptDropBuilder;
import com.codetaylor.mc.dropt.api.api.IDroptHarvesterRuleBuilder;
import com.codetaylor.mc.dropt.api.api.IDroptRuleBuilder;
import com.codetaylor.mc.dropt.api.api.RandomFortuneInt;
import com.codetaylor.mc.dropt.api.reference.EnumDropStrategy;
import com.codetaylor.mc.dropt.api.reference.EnumListType;
import com.codetaylor.mc.dropt.api.reference.EnumReplaceStrategy;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleDrop;

import java.util.ArrayList;
import java.util.List;

public class RuleBuilder
    implements IDroptRuleBuilder {

  private final Rule rule;

  public RuleBuilder() {

    this.rule = new Rule();
  }

  @Override
  public IDroptRuleBuilder debug() {

    this.rule.debug = true;
    return this;
  }

  @Override
  public IDroptRuleBuilder matchBlocks(String[] blockStrings) {

    this.rule.match.blocks.blocks = ArrayHelper.copy(blockStrings);
    return this;
  }

  @Override
  public IDroptRuleBuilder matchBlocks(EnumListType type, String[] blockStrings) {

    this.rule.match.blocks.type = type;
    this.rule.match.blocks.blocks = ArrayHelper.copy(blockStrings);
    return this;
  }

  @Override
  public IDroptRuleBuilder matchDrops(String[] items) {

    this.rule.match.drops.drops = ArrayHelper.copy(items);
    return this;
  }

  @Override
  public IDroptRuleBuilder matchDrops(EnumListType type, String[] items) {

    this.rule.match.drops.type = type;
    this.rule.match.drops.drops = ArrayHelper.copy(items);
    return this;
  }

  @Override
  public IDroptRuleBuilder matchHarvester(IDroptHarvesterRuleBuilder builder) {

    this.rule.match.harvester = ((HarvesterRuleBuilder) builder).build();
    return this;
  }

  @Override
  public IDroptRuleBuilder matchBiomes(String[] ids) {

    this.rule.match.biomes.ids = ArrayHelper.copy(ids);
    return this;
  }

  @Override
  public IDroptRuleBuilder matchBiomes(EnumListType type, String[] ids) {

    this.rule.match.biomes.type = type;
    this.rule.match.biomes.ids = ArrayHelper.copy(ids);
    return this;
  }

  @Override
  public IDroptRuleBuilder matchDimensions(int[] ids) {

    this.rule.match.dimensions.ids = ArrayHelper.copy(ids);
    return this;
  }

  @Override
  public IDroptRuleBuilder matchDimensions(EnumListType type, int[] ids) {

    this.rule.match.dimensions.type = type;
    this.rule.match.dimensions.ids = ArrayHelper.copy(ids);
    return this;
  }

  @Override
  public IDroptRuleBuilder matchVerticalRange(int min, int max) {

    this.rule.match.verticalRange.min = min;
    this.rule.match.verticalRange.max = max;
    return this;
  }

  @Override
  public IDroptRuleBuilder replaceStrategy(EnumReplaceStrategy strategy) {

    this.rule.replaceStrategy = strategy;
    return this;
  }

  @Override
  public IDroptRuleBuilder dropStrategy(EnumDropStrategy strategy) {

    this.rule.dropStrategy = strategy;
    return this;
  }

  @Override
  public IDroptRuleBuilder dropCount(RandomFortuneInt range) {

    this.rule.dropCount.fixed = range.fixed;
    this.rule.dropCount.min = range.min;
    this.rule.dropCount.max = range.max;
    this.rule.dropCount.fortuneModifier = range.fortuneModifier;
    return this;
  }

  @Override
  public IDroptRuleBuilder addDrops(IDroptDropBuilder[] drops) {

    List<RuleDrop> list = new ArrayList<>(drops.length);

    for (IDroptDropBuilder drop : drops) {
      list.add(((DropBuilder) drop).build());
    }

    this.rule.drops = list.toArray(new RuleDrop[drops.length]);

    return this;
  }

  public Rule build() {

    return this.rule;
  }
}
