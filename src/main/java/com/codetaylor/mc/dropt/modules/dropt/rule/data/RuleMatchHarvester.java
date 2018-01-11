package com.codetaylor.mc.dropt.modules.dropt.rule.data;

public class RuleMatchHarvester {

  public EnumListType type = EnumListType.WHITELIST;
  public EnumHarvesterType harvester = EnumHarvesterType.ANY;
  public RuleMatchHarvesterGameStage gamestages = new RuleMatchHarvesterGameStage();
  public RuleMatchHarvesterHeldItemMainHand heldItemMainHand = new RuleMatchHarvesterHeldItemMainHand();
  public RuleMatchHarvesterPlayerName playerName = new RuleMatchHarvesterPlayerName();

}
