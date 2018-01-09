package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.rule.ItemMatcher;
import com.codetaylor.mc.dropt.modules.dropt.rule.LogFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.WeightedPicker;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Rule {

  public boolean debug = false;
  public RuleMatch match = new RuleMatch();
  public EnumReplaceStrategy replaceStrategy = EnumReplaceStrategy.REPLACE_ALL;
  public RandomFortuneInt dropCount = new RandomFortuneInt();
  public RuleDrop[] drops = new RuleDrop[0];

  public List<ItemStack> modifyDrops(
      List<ItemStack> currentDrops,
      boolean isSilkTouching,
      int fortuneLevel,
      LogFileWrapper logFile,
      boolean debug
  ) {

    if (debug) {
      logFile.debug("");
    }

    if (this.replaceStrategy == EnumReplaceStrategy.REPLACE_ALL) {
      currentDrops.clear();

      if (debug) {
        logFile.debug("[DROP] Cleared drop list, replace strategy: " + this.replaceStrategy);
      }
    }

    WeightedPicker<RuleDropItem> picker = new WeightedPicker<>();

    if (debug) {
      logFile.debug("[DROP] Selecting drop candidates...");
    }

    int candidatesFound = 0;

    for (RuleDrop drop : this.drops) {

      if (drop.selector.isValidCandidate(isSilkTouching, fortuneLevel, logFile, debug)) {
        int weight = drop.selector.weight.value + (fortuneLevel * drop.selector.weight.fortuneModifier);
        picker.add(weight, drop.item);
        candidatesFound += 1;

        if (debug) {
          logFile.debug("[DROP] Added drop to weighted picker: " + drop.item.toString());
          logFile.debug("[DROP] Added drop using weight value: " + weight);
        }
      }
    }

    if (picker.getTotal() == 0) {

      if (debug) {
        logFile.debug("[DROP] No valid drop candidates were found");
        logFile.debug("[DROP] Returning drop list: " + currentDrops);
      }
      return currentDrops;

    } else if (debug) {
      logFile.debug("[DROP] Valid drop candidates found: " + candidatesFound);
      logFile.debug("[DROP] Total weight: " + picker.getTotal());
    }

    int dropCount = this.dropCount.get(ModuleDropt.RANDOM, fortuneLevel);

    if (debug) {
      logFile.debug("[DROP] Drop count: " + dropCount);
    }

    List<ItemStack> newDrops = new ArrayList<>();

    for (int i = 0; i < dropCount; i++) {
      RuleDropItem ruleDropItem = picker.get(ModuleDropt.RANDOM.nextInt(picker.getTotal()));
      int itemQuantity = ruleDropItem.quantity.get(ModuleDropt.RANDOM, fortuneLevel);

      if (debug) {
        logFile.debug("[DROP] Selected drop: " + ruleDropItem.toString());
        logFile.debug("[DROP] Selected drop quantity: " + itemQuantity);
      }

      if (itemQuantity <= 0) {
        logFile.debug("[DROP] Skipping selected drop due to low item quantity roll: " + itemQuantity);
        continue;
      }

      if (ruleDropItem._items.isEmpty()) {
        logFile.debug("[DROP] Skipping selected drop due to empty drop item list");
        continue;
      }

      ItemStack copy = ruleDropItem._items.get(ModuleDropt.RANDOM.nextInt(ruleDropItem._items.size())).copy();
      copy.setCount(itemQuantity);
      newDrops.add(copy);

      if (debug) {
        logFile.debug("[DROP] Added ItemStack to drop list: " + copy);
      }
    }

    if (this.replaceStrategy == EnumReplaceStrategy.REPLACE_ALL_IF_SELECTED
        && !newDrops.isEmpty()) {
      currentDrops.clear();

      if (debug) {
        logFile.debug("[DROP] Cleared drop list, replace strategy: " + this.replaceStrategy);
      }
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

      if (debug) {
        logFile.debug("[DROP] Removing all items specified in the item matcher, replace strategy: " + this.replaceStrategy);
      }

      for (Iterator<ItemStack> it = currentDrops.iterator(); it.hasNext(); ) {
        ItemStack itemStack = it.next();

        for (ItemMatcher itemMatcher : this.match._items) {

          if (itemMatcher.matches(itemStack, logFile, debug, "[DROP] ")) {
            it.remove();

            if (debug) {
              logFile.debug("[DROP] Removed: " + itemStack);
            }
            break;

          } else if (debug) {
            logFile.debug("[DROP] Skipping: " + itemStack);
          }
        }
      }
    }

    currentDrops.addAll(newDrops);

    if (debug) {
      logFile.debug("Returning drop list: " + currentDrops);
    }

    return currentDrops;
  }
}
