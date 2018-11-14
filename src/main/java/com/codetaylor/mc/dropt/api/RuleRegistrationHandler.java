package com.codetaylor.mc.dropt.api;

import com.codetaylor.mc.dropt.api.api.IDroptRuleBuilder;
import com.codetaylor.mc.dropt.api.api.IRuleRegistrationHandler;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class RuleRegistrationHandler
    implements IRuleRegistrationHandler {

  private final List<RuleList> ruleLists;

  public RuleRegistrationHandler(List<RuleList> ruleLists) {

    this.ruleLists = ruleLists;
  }

  @Override
  public void register(ResourceLocation id, int priority, List<IDroptRuleBuilder> builders) {

    RuleList ruleList = new RuleList();
    ruleList._filename = id.toString();
    ruleList.priority = priority;

    for (IDroptRuleBuilder builder : builders) {
      ruleList.rules.add(((RuleBuilder) builder).build());
    }

    this.ruleLists.add(ruleList);
  }

}
