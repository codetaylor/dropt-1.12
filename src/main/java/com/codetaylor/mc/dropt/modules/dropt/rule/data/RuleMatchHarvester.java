package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.api.reference.EnumHarvesterType;

public class RuleMatchHarvester {

  public EnumHarvesterType type = EnumHarvesterType.ANY;
  public RuleMatchHarvesterGameStage gamestages = new RuleMatchHarvesterGameStage();
  public RuleMatchHarvesterHeldItem heldItemMainHand = new RuleMatchHarvesterHeldItem();
  public RuleMatchHarvesterHeldItem heldItemOffHand = new RuleMatchHarvesterHeldItem();
  public RuleMatchHarvesterPlayerName playerName = new RuleMatchHarvesterPlayerName();

}
