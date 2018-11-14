package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.api.reference.EnumHarvesterGameStageType;
import com.codetaylor.mc.dropt.api.reference.EnumListType;

public class RuleMatchHarvesterGameStage {

  public EnumListType type = EnumListType.WHITELIST;
  public EnumHarvesterGameStageType require = EnumHarvesterGameStageType.ANY;
  public String[] stages = new String[0];

}
