package com.codetaylor.mc.dropt.api;

import com.codetaylor.mc.dropt.api.api.*;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.function.Supplier;

public final class DroptAPI {

  private static final Supplier<IDroptRuleBuilder> SUPPLIER_RULE_BUILDER = null;
  private static final Supplier<IDroptHarvesterRuleBuilder> SUPPLIER_HARVESTER_RULE_BUILDER = null;
  private static final Supplier<IDroptDropBuilder> SUPPLIER_DROP_BUILDER = null;
  private static final IRuleRegistrationHandler CONSUMER_RULE = null;

  public static void registerRuleList(ResourceLocation id, int priority, List<IDroptRuleBuilder> builders) {

    CONSUMER_RULE.register(id, priority, builders);
  }

  public static IDroptRuleBuilder rule() {

    return SUPPLIER_RULE_BUILDER.get();
  }

  public static IDroptHarvesterRuleBuilder harvester() {

    return SUPPLIER_HARVESTER_RULE_BUILDER.get();
  }

  public static IDroptDropBuilder drop() {

    return SUPPLIER_DROP_BUILDER.get();
  }

  public static RandomFortuneInt range(int fixed) {

    RandomFortuneInt result = new RandomFortuneInt();
    result.fixed = fixed;
    return result;
  }

  public static RandomFortuneInt range(int min, int max) {

    RandomFortuneInt result = new RandomFortuneInt();
    result.min = min;
    result.max = max;
    return result;
  }

  public static RandomFortuneInt range(int min, int max, int fortuneModifier) {

    RandomFortuneInt result = new RandomFortuneInt();
    result.min = min;
    result.max = max;
    result.fortuneModifier = fortuneModifier;
    return result;
  }

  public static RuleDropSelectorWeight weight(int weight) {

    RuleDropSelectorWeight result = new RuleDropSelectorWeight();
    result.value = weight;
    return result;
  }

  public static RuleDropSelectorWeight weight(int weight, int fortuneModifier) {

    RuleDropSelectorWeight result = new RuleDropSelectorWeight();
    result.value = weight;
    result.fortuneModifier = fortuneModifier;
    return result;
  }

}
