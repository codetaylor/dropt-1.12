package com.codetaylor.mc.dropt.modules.dropt.rule.data;

public class Rule {

  public boolean debug = false;
  public RuleMatch match = new RuleMatch();
  public EnumReplaceStrategy replaceStrategy = EnumReplaceStrategy.REPLACE_ALL;
  public EnumDropStrategy dropStrategy = EnumDropStrategy.REPEAT;
  public RandomFortuneInt dropCount = new RandomFortuneInt();
  public RuleDrop[] drops = new RuleDrop[0];

}
