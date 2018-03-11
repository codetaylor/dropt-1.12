package com.codetaylor.mc.dropt.modules.dropt.rule.data;

public class RuleDrop {

  public RuleDropSelector selector = new RuleDropSelector();
  public RuleDropItem item = new RuleDropItem();
  public RandomFortuneInt xp = new RandomFortuneInt(0, 0);
  public EnumXPReplaceStrategy xpReplaceStrategy = EnumXPReplaceStrategy.ADD;

}
