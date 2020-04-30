package com.codetaylor.mc.dropt.api;

import com.codetaylor.mc.athenaeum.util.ArrayHelper;
import com.codetaylor.mc.dropt.api.api.IDroptDropBuilder;
import com.codetaylor.mc.dropt.api.api.RandomFortuneInt;
import com.codetaylor.mc.dropt.api.api.RuleDropSelectorWeight;
import com.codetaylor.mc.dropt.api.reference.EnumDropListStrategy;
import com.codetaylor.mc.dropt.api.reference.EnumSilktouch;
import com.codetaylor.mc.dropt.api.reference.EnumXPReplaceStrategy;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleDrop;

import java.util.Map;

public class DropBuilder
    implements IDroptDropBuilder {

  private final RuleDrop rule;

  public DropBuilder() {

    this.rule = new RuleDrop();
  }

  @Override
  public IDroptDropBuilder force() {

    this.rule.force = true;
    return this;
  }

  @Override
  public IDroptDropBuilder selector(RuleDropSelectorWeight weight) {

    this.rule.selector.weight.value = weight.value;
    this.rule.selector.weight.fortuneModifier = weight.fortuneModifier;
    return this;
  }

  @Override
  public IDroptDropBuilder selector(RuleDropSelectorWeight weight, int fortuneLevelRequired) {

    this.rule.selector.weight.value = weight.value;
    this.rule.selector.weight.fortuneModifier = weight.fortuneModifier;
    this.rule.selector.fortuneLevelRequired = fortuneLevelRequired;
    return this;
  }

  @Override
  public IDroptDropBuilder selector(RuleDropSelectorWeight weight, EnumSilktouch silktouch) {

    this.rule.selector.weight.value = weight.value;
    this.rule.selector.weight.fortuneModifier = weight.fortuneModifier;
    this.rule.selector.silktouch = silktouch;
    return this;
  }

  @Override
  public IDroptDropBuilder selector(RuleDropSelectorWeight weight, EnumSilktouch silktouch, int fortuneLevelRequired) {

    this.rule.selector.weight.value = weight.value;
    this.rule.selector.weight.fortuneModifier = weight.fortuneModifier;
    this.rule.selector.silktouch = silktouch;
    this.rule.selector.fortuneLevelRequired = fortuneLevelRequired;
    return this;
  }

  @Override
  public IDroptDropBuilder items(String[] items) {

    this.rule.item.items = ArrayHelper.copy(items);
    return this;
  }

  @Override
  public IDroptDropBuilder items(EnumDropListStrategy dropListStrategy, String[] items) {

    this.rule.item.drop = dropListStrategy;
    return this.items(items);
  }

  @Override
  public IDroptDropBuilder items(String[] items, RandomFortuneInt count) {

    this.rule.item.items = ArrayHelper.copy(items);
    this.rule.item.quantity.fixed = count.fixed;
    this.rule.item.quantity.min = count.min;
    this.rule.item.quantity.max = count.max;
    this.rule.item.quantity.fortuneModifier = count.fortuneModifier;
    return this;
  }

  @Override
  public IDroptDropBuilder items(EnumDropListStrategy dropListStrategy, String[] items, RandomFortuneInt count) {

    this.rule.item.drop = dropListStrategy;
    return this.items(items, count);
  }

  @Override
  public IDroptDropBuilder matchQuantity(String[] drops) {

    this.rule.item.matchQuantity.drops = drops;
    return this;
  }

  @Override
  public IDroptDropBuilder xp(EnumXPReplaceStrategy replace, RandomFortuneInt amount) {

    this.rule.xpReplaceStrategy = replace;
    this.rule.xp.fixed = amount.fixed;
    this.rule.xp.min = amount.min;
    this.rule.xp.max = amount.max;
    this.rule.xp.fortuneModifier = amount.fortuneModifier;
    return this;
  }

  @Override
  public IDroptDropBuilder replaceBlock(String block, Map<String, String> properties) {

    this.rule.replaceBlock.block = block;
    this.rule.replaceBlock.properties.putAll(properties);
    return this;
  }

  public RuleDrop build() {

    return this.rule;
  }
}
