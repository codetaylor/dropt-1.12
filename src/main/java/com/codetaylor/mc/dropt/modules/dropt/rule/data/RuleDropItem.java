package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RuleDropItem {

  public transient List<ItemStack> _items = new ArrayList<>();

  public String[] items = new String[0];
  public RandomFortuneInt quantity = new RandomFortuneInt(1);

  @Override
  public String toString() {

    return "RuleDropItem{" +
        "_items=" + _items +
        ", quantity=" + quantity +
        '}';
  }
}
