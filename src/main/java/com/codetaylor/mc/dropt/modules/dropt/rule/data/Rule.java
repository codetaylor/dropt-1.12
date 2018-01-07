package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.rule.ItemMatcher;
import com.codetaylor.mc.dropt.modules.dropt.rule.WeightedPicker;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Rule {

  public RuleMatch match = new RuleMatch();
  public EnumReplaceStrategy replaceStrategy = EnumReplaceStrategy.REPLACE_ALL;
  public RandomFortuneInt dropCount = new RandomFortuneInt();
  public RuleDrop[] drops = new RuleDrop[0];

  public List<ItemStack> modifyDrops(List<ItemStack> currentDrops, boolean isSilkTouching, int fortuneLevel) {

    if (this.replaceStrategy == EnumReplaceStrategy.REPLACE_ALL) {
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
      // no valid candidates were found
      return currentDrops;
    }

    int dropCount = this.dropCount.get(ModuleDropt.RANDOM, fortuneLevel);

    List<ItemStack> newDrops = new ArrayList<>();

    for (int i = 0; i < dropCount; i++) {
      RuleDropItem ruleDropItem = picker.get(ModuleDropt.RANDOM.nextInt(picker.getTotal()));
      int itemQuantity = ruleDropItem.quantity.get(ModuleDropt.RANDOM, fortuneLevel);

      if (itemQuantity > 0 && ruleDropItem._items.size() > 0) {
        ItemStack copy = ruleDropItem._items.get(ModuleDropt.RANDOM.nextInt(ruleDropItem._items.size())).copy();
        copy.setCount(itemQuantity);
        newDrops.add(copy);
      }
    }

    if (this.replaceStrategy == EnumReplaceStrategy.REPLACE_ALL_IF_SELECTED
        && !newDrops.isEmpty()) {
      currentDrops.clear();
    }

    boolean removeMatchedItems = false;

    if (this.replaceStrategy == EnumReplaceStrategy.REPLACE_ITEMS
        && !this.match._items.isEmpty()) {
      removeMatchedItems = true;
    }

    if (this.replaceStrategy == EnumReplaceStrategy.REPLACE_ITEMS_IF_SELECTED
        && !newDrops.isEmpty()
        && !this.match._items.isEmpty()) {
      removeMatchedItems = true;
    }

    if (removeMatchedItems) {
      // Remove all items specified in the item matcher.

      for (Iterator<ItemStack> it = currentDrops.iterator(); it.hasNext(); ) {
        ItemStack itemStack = it.next();

        for (ItemMatcher itemMatcher : this.match._items) {

          if (itemMatcher.matches(itemStack)) {
            it.remove();
            break;
          }
        }
      }
    }

    currentDrops.addAll(newDrops);
    return currentDrops;
  }
}
