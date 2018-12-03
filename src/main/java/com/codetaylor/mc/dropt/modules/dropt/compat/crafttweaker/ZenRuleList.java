package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.dropt.api.api.IDroptRuleBuilder;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@ZenDocClass(value = "mods.dropt.RuleList", description = {
    "This class is a container for rules.",
    "@see /json/syntax/#irulelist"
})
@ZenClass("mods.dropt.RuleList")
public class ZenRuleList {

  private List<IDroptRuleBuilder> ruleList;
  private ResourceLocation resourceLocation;
  private int priority;

  /* package */ ZenRuleList(ResourceLocation resourceLocation) {

    this.ruleList = new ArrayList<>();
    this.resourceLocation = resourceLocation;
  }

  @ZenDocMethod(
      order = 1,
      description = {
          "Set the priority of this rule list.",
          "Rule lists with a larger priority will be matched first.",
          "@see /json/syntax/#irulelist"
      },
      args = {"priority"}
  )
  @ZenMethod
  public ZenRuleList priority(int priority) {

    this.priority = priority;
    return this;
  }

  @ZenDocMethod(
      order = 2,
      description = {
          "Add a configured rule to this rule list.",
          "@see /json/syntax/#irulelist"
      },
      args = {"rule"}
  )
  @ZenMethod
  public ZenRuleList add(ZenRule rule) {

    this.ruleList.add(rule.getRule());
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
