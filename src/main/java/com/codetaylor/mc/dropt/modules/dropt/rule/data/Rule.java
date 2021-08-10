package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.api.reference.EnumDropStrategy;
import com.codetaylor.mc.dropt.api.reference.EnumReplaceStrategy;
import com.codetaylor.mc.dropt.api.api.RandomFortuneInt;

public class Rule {

  public boolean debug = false;
  public RuleMatch match = new RuleMatch();
  public EnumReplaceStrategy replaceStrategy = EnumReplaceStrategy.REPLACE_ALL;
  public EnumDropStrategy dropStrategy = EnumDropStrategy.REPEAT;
  public RandomFortuneInt dropCount = new RandomFortuneInt(1);
  public RuleDrop[] drops = new RuleDrop[0];
  public boolean fallthrough = false;

}
