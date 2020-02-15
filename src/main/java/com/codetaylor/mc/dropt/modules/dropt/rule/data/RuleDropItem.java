package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.api.api.RandomFortuneInt;
import com.codetaylor.mc.dropt.api.reference.EnumDropListStrategy;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RuleDropItem {

  public transient List<ItemStack> _items = new ArrayList<>();

  public EnumDropListStrategy drop = EnumDropListStrategy.ONE;
  public String[] items = new String[0];
  public RandomFortuneInt quantity = new RandomFortuneInt(1);
  public RuleDropItemMatchQuantity matchQuantity = new RuleDropItemMatchQuantity();
}
