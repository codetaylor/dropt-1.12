package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.api.api.RandomFortuneInt;
import com.codetaylor.mc.dropt.api.reference.EnumXPReplaceStrategy;

public class RuleDrop {

  public boolean force = false;
  public RuleDropSelector selector = new RuleDropSelector();
  public RuleDropItem item = new RuleDropItem();
  public RandomFortuneInt xp = new RandomFortuneInt();
  public EnumXPReplaceStrategy xpReplaceStrategy = EnumXPReplaceStrategy.ADD;
  public RuleDropReplaceBlock replaceBlock = new RuleDropReplaceBlock();

}
