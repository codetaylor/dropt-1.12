package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RuleMatchHarvester {

  public transient List<ItemStack> _heldItemMainHand = new ArrayList<>();

  public EnumHarvesterType type = EnumHarvesterType.ANY;
  public RuleMatchHarvesterGameStage gamestages = new RuleMatchHarvesterGameStage();
  public String[] heldItemMainHand = new String[0];
  public String[] playerName = new String[0];

}
