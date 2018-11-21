package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.dropt.api.api.IDroptRuleBuilder;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@ZenClass("mods.dropt.RuleList")
public class ZenRuleList {

  private List<IDroptRuleBuilder> ruleList;
  private ResourceLocation resourceLocation;
  private int priority;

  /* package */ ZenRuleList(ResourceLocation resourceLocation) {

    this.ruleList = new ArrayList<>();
    this.resourceLocation = resourceLocation;
  }

  @ZenMethod
  public ZenRuleList add(ZenRule rule) {

    this.ruleList.add(rule.getRule());
    return this;
  }

  @ZenMethod
  public ZenRuleList priority(int priority) {

    this.priority = priority;
    return this;
  }

  public List<IDroptRuleBuilder> getRuleList() {

    return this.ruleList;
  }

  public ResourceLocation getResourceLocation() {

    return this.resourceLocation;
  }

  public int getPriority() {

    return this.priority;
  }
}
