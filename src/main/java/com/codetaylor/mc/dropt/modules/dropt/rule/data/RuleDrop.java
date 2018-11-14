package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.api.reference.EnumXPReplaceStrategy;
import com.codetaylor.mc.dropt.api.api.RandomFortuneInt;

public class RuleDrop {

  public RuleDropSelector selector = new RuleDropSelector();
  public RuleDropItem item = new RuleDropItem();
  public RandomFortuneInt xp = new RandomFortuneInt();
  public EnumXPReplaceStrategy xpReplaceStrategy = EnumXPReplaceStrategy.ADD;

}
