package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.api.reference.EnumListType;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RuleMatchHarvesterHeldItem {

  public transient List<ItemStack> _items = new ArrayList<>();
  public transient String _toolClass = null;
  public transient int _minHarvestLevel = Integer.MIN_VALUE;
  public transient int _maxHarvestLevel = Integer.MAX_VALUE;

  public EnumListType type = EnumListType.WHITELIST;
  public String[] items = new String[0];
  public String harvestLevel = null; // "pickaxe;min;max"
}
