package com.codetaylor.mc.dropt.api.api;

import com.codetaylor.mc.dropt.api.reference.EnumDropListStrategy;
import com.codetaylor.mc.dropt.api.reference.EnumSilktouch;
import com.codetaylor.mc.dropt.api.reference.EnumXPReplaceStrategy;

import java.util.Map;

public interface IDroptDropBuilder {

  IDroptDropBuilder force();

  IDroptDropBuilder selector(RuleDropSelectorWeight weight);

  IDroptDropBuilder selector(RuleDropSelectorWeight weight, int fortuneLevelRequired);

  IDroptDropBuilder selector(RuleDropSelectorWeight weight, EnumSilktouch silktouch);

  IDroptDropBuilder selector(RuleDropSelectorWeight weight, EnumSilktouch silktouch, int fortuneLevelRequired);

  IDroptDropBuilder items(String[] items);

  IDroptDropBuilder items(EnumDropListStrategy dropListStrategy, String[] items);

  IDroptDropBuilder items(String[] items, RandomFortuneInt count);

  IDroptDropBuilder items(EnumDropListStrategy dropListStrategy, String[] items, RandomFortuneInt count);

  IDroptDropBuilder matchQuantity(String[] drops);

  IDroptDropBuilder xp(EnumXPReplaceStrategy replace, RandomFortuneInt amount);

  IDroptDropBuilder replaceBlock(String block, Map<String, String> properties);

}