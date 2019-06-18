package com.codetaylor.mc.dropt.api;

import com.codetaylor.mc.athenaeum.util.ArrayHelper;
import com.codetaylor.mc.dropt.api.api.IDroptHarvesterRuleBuilder;
import com.codetaylor.mc.dropt.api.reference.EnumHarvesterGameStageType;
import com.codetaylor.mc.dropt.api.reference.EnumHarvesterType;
import com.codetaylor.mc.dropt.api.reference.EnumListType;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchHarvester;

public class HarvesterRuleBuilder
    implements IDroptHarvesterRuleBuilder {

  private final RuleMatchHarvester rule;

  public HarvesterRuleBuilder() {

    this.rule = new RuleMatchHarvester();
  }

  @Override
  public IDroptHarvesterRuleBuilder type(EnumHarvesterType type) {

    this.rule.type = type;
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder mainHand(String[] items) {

    this.rule.heldItemMainHand.items = ArrayHelper.copy(items);
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder mainHand(String harvestLevel) {

    this.rule.heldItemMainHand.harvestLevel = harvestLevel;
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder mainHand(String[] items, String harvestLevel) {

    this.rule.heldItemMainHand.items = ArrayHelper.copy(items);
    this.rule.heldItemMainHand.harvestLevel = harvestLevel;
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder mainHand(EnumListType type, String[] items) {

    this.rule.heldItemMainHand.type = type;
    this.rule.heldItemMainHand.items = ArrayHelper.copy(items);
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder mainHand(EnumListType type, String harvestLevel) {

    this.rule.heldItemMainHand.type = type;
    this.rule.heldItemMainHand.harvestLevel = harvestLevel;
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder mainHand(EnumListType type, String[] items, String harvestLevel) {

    this.rule.heldItemMainHand.type = type;
    this.rule.heldItemMainHand.items = ArrayHelper.copy(items);
    this.rule.heldItemMainHand.harvestLevel = harvestLevel;
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder offHand(String[] items) {

    this.rule.heldItemOffHand.items = ArrayHelper.copy(items);
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder offHand(String harvestLevel) {

    this.rule.heldItemOffHand.harvestLevel = harvestLevel;
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder offHand(String[] items, String harvestLevel) {

    this.rule.heldItemOffHand.items = ArrayHelper.copy(items);
    this.rule.heldItemOffHand.harvestLevel = harvestLevel;
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder offHand(EnumListType type, String[] items) {

    this.rule.heldItemOffHand.type = type;
    this.rule.heldItemOffHand.items = ArrayHelper.copy(items);
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder offHand(EnumListType type, String harvestLevel) {

    this.rule.heldItemOffHand.type = type;
    this.rule.heldItemOffHand.harvestLevel = harvestLevel;
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder offHand(EnumListType type, String[] items, String harvestLevel) {

    this.rule.heldItemOffHand.type = type;
    this.rule.heldItemOffHand.items = ArrayHelper.copy(items);
    this.rule.heldItemOffHand.harvestLevel = harvestLevel;
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder gameStages(String[] stages) {

    this.rule.gamestages.stages = ArrayHelper.copy(stages);
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder gameStages(EnumHarvesterGameStageType require, String[] stages) {

    this.rule.gamestages.require = require;
    this.rule.gamestages.stages = ArrayHelper.copy(stages);
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder gameStages(EnumListType type, EnumHarvesterGameStageType require, String[] stages) {

    this.rule.gamestages.type = type;
    this.rule.gamestages.require = require;
    this.rule.gamestages.stages = ArrayHelper.copy(stages);
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder playerName(String[] names) {

    this.rule.playerName.names = ArrayHelper.copy(names);
    return this;
  }

  @Override
  public IDroptHarvesterRuleBuilder playerName(EnumListType type, String[] names) {

    this.rule.playerName.type = type;
    this.rule.playerName.names = ArrayHelper.copy(names);
    return this;
  }

  public RuleMatchHarvester build() {

    return this.rule;
  }
}
