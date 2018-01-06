package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.rule.WeightedPicker;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Rule {

  public RuleMatch match = new RuleMatch();
  public EnumMergeStrategy mergeStrategy = EnumMergeStrategy.ADD;
  public RangeInt dropCount = new RangeInt();
  public RuleDrop[] drops = new RuleDrop[0];

  public List<ItemStack> modifyDrops(List<ItemStack> currentDrops, boolean isSilkTouching, int fortuneLevel) {

    if (this.mergeStrategy == EnumMergeStrategy.REPLACE) {
      currentDrops.clear();
    }

    WeightedPicker<RuleDropItem> picker = new WeightedPicker<>();

    for (RuleDrop drop : this.drops) {

      if (drop.selector.isValidCandidate(isSilkTouching, fortuneLevel)) {
        int weight = drop.selector.weight.value + (fortuneLevel * drop.selector.weight.fortuneModifier);
        picker.add(weight, drop.item);
      }
    }

    if (picker.getTotal() == 0) {
      // no valid candidate drops were found
      return currentDrops;
    }

    int dropCount = this.dropCount.get(ModuleDropt.RANDOM, fortuneLevel);

    List<ItemStack> newDrops = new ArrayList<>();

    for (int i = 0; i < dropCount; i++) {
      RuleDropItem ruleDropItem = picker.get(ModuleDropt.RANDOM.nextInt(picker.getTotal()));
      int itemQuantity = ruleDropItem.quantity.get(ModuleDropt.RANDOM, fortuneLevel);

      if (itemQuantity > 0) {
        ItemStack copy = ruleDropItem._item.copy();
        copy.setCount(itemQuantity);
        newDrops.add(copy);
      }
    }

    if (this.mergeStrategy == EnumMergeStrategy.REPLACE_IF_SELECTED
        && !newDrops.isEmpty()) {
      currentDrops.clear();
    }

    currentDrops.addAll(newDrops);
    return currentDrops;
  }
}
