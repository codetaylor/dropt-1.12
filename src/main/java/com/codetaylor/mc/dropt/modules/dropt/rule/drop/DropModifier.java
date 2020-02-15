package com.codetaylor.mc.dropt.modules.dropt.rule.drop;

import com.codetaylor.mc.athenaeum.util.WeightedPicker;
import com.codetaylor.mc.dropt.api.reference.EnumDropListStrategy;
import com.codetaylor.mc.dropt.api.reference.EnumDropStrategy;
import com.codetaylor.mc.dropt.api.reference.EnumReplaceStrategy;
import com.codetaylor.mc.dropt.api.reference.EnumXPReplaceStrategy;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleDrop;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.ItemMatchEntry;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class DropModifier {

  private static final Random RANDOM = new Random();

  public List<ItemStack> modifyDrops(
      World world,
      BlockPos pos,
      Rule rule,
      List<ItemStack> currentDrops,
      boolean isSilkTouching,
      int fortuneLevel,
      int experience,
      DebugFileWrapper logFile,
      boolean debug
  ) {

    if (debug) {
      logFile.debug("");
    }

    // Make a copy of the current drops before we start altering them.
    // This is useful later if we need to count the current drops.
    List<ItemStack> originalDrops = new ArrayList<>(currentDrops);

    if (rule.replaceStrategy == EnumReplaceStrategy.REPLACE_ALL) {
      currentDrops.clear();

      if (debug) {
        logFile.debug("[DROP] Cleared drop list, replace strategy: " + rule.replaceStrategy);
      }
    }

    WeightedPicker<RuleDrop> picker = new WeightedPicker<>();

    if (debug) {
      logFile.debug("[DROP] Selecting drop candidates...");
    }

    int candidatesFound = 0;
    List<RuleDrop> forcedDrops = new ArrayList<>();

    for (RuleDrop drop : rule.drops) {

      if (drop.force) {
        forcedDrops.add(drop);

      } else if (drop.selector.isValidCandidate(isSilkTouching, fortuneLevel, logFile, debug)) {
        int weight = drop.selector.weight.value + (fortuneLevel * drop.selector.weight.fortuneModifier);

        if (weight > 0) {
          picker.add(weight, drop);
          candidatesFound += 1;

          if (debug) {
            logFile.debug("[DROP] Added drop to weighted picker: " + drop.item.toString());
            logFile.debug("[DROP] Added drop using weight value: " + weight);
          }

        } else if (debug) {
          logFile.debug("[DROP] Skipped adding drop " + drop.item.toString() + " to weighted picker due to calculated weight <= 0: " + weight);
        }
      }
    }

    if (picker.getTotal() == 0 && forcedDrops.isEmpty()) {

      if (debug) {
        logFile.debug("[DROP] No valid drop candidates were found");
        logFile.debug("[DROP] Returning drop list: " + currentDrops);
      }
      return currentDrops;

    } else if (debug) {
      logFile.debug("[DROP] Valid drop candidates found: " + candidatesFound);
      logFile.debug("[DROP] Forced drops found: " + forcedDrops.size());
      logFile.debug("[DROP] Total weight: " + picker.getTotal());
    }

    int dropCount = rule.dropCount.get(RANDOM, fortuneLevel);

    if (debug) {
      logFile.debug("[DROP] Drop count: " + dropCount);
    }

    List<ItemStack> newDrops = new ArrayList<>();
    List<RuleDrop> pickedDrops = new ArrayList<>(forcedDrops);

    for (int i = 0; i < dropCount; i++) {

      if (picker.getSize() == 0) {
        break;
      }

      RuleDrop ruleDrop;

      if (rule.dropStrategy == EnumDropStrategy.UNIQUE) {
        ruleDrop = picker.getAndRemove();

        if (debug) {
          logFile.debug("[DROP] Removed drop from picker: " + ruleDrop.toString());
        }

      } else {
        ruleDrop = picker.get();
      }

      pickedDrops.add(ruleDrop);
    }

    for (RuleDrop ruleDrop : pickedDrops) {
      int itemQuantity = this.getItemQuantity(fortuneLevel, logFile, debug, originalDrops, ruleDrop);

      if (debug) {
        logFile.debug("[DROP] Selected drop: " + ruleDrop.toString());
        logFile.debug("[DROP] Selected drop quantity: " + itemQuantity);
      }

      int xp = ruleDrop.xp.get(RANDOM, fortuneLevel);

      if (ruleDrop.xpReplaceStrategy == EnumXPReplaceStrategy.ADD) {
        xp += experience;
      }

      if (xp > 0) {

        if (debug) {
          logFile.debug("[DROP] Dropping xp: " + xp);
        }

        while (xp > 0) {
          int xpDrop = EntityXPOrb.getXPSplit(xp);
          xp -= xpDrop;
          world.spawnEntity(new EntityXPOrb(world, pos.getX(), pos.getY() + 0.5, pos.getZ(), xpDrop));
        }

      } else if (debug) {
        logFile.debug("[DROP] Dropped zero xp");
      }

      if (itemQuantity <= 0) {

        if (debug) {
          logFile.debug("[DROP] Skipping selected drop due to low item quantity roll: " + itemQuantity);
        }
        continue;
      }

      if (ruleDrop.item._items.isEmpty()) {

        if (debug) {
          logFile.debug("[DROP] Skipping selected drop due to empty drop item list");
        }
        continue;
      }

      if (ruleDrop.item.drop == EnumDropListStrategy.ONE) {
        ItemStack copy = ruleDrop.item._items.get(RANDOM.nextInt(ruleDrop.item._items.size())).copy();
        this.addDrop(logFile, debug, newDrops, itemQuantity, copy);

      } else {
        for (ItemStack item : ruleDrop.item._items) {
          this.addDrop(logFile, debug, newDrops, itemQuantity, item.copy());
        }
      }
    }

    if (rule.replaceStrategy == EnumReplaceStrategy.REPLACE_ALL_IF_SELECTED
        && !newDrops.isEmpty()) {
      currentDrops.clear();

      if (debug) {
        logFile.debug("[DROP] Cleared drop list, replace strategy: " + rule.replaceStrategy);
      }
    }

    boolean removeMatchedItems = false;

    if (rule.replaceStrategy == EnumReplaceStrategy.REPLACE_ITEMS
        && !rule.match.drops._drops.isEmpty()) {
      removeMatchedItems = true;
    }

    if (rule.replaceStrategy == EnumReplaceStrategy.REPLACE_ITEMS_IF_SELECTED
        && !newDrops.isEmpty()
        && !rule.match.drops._drops.isEmpty()) {
      removeMatchedItems = true;
    }

    if (removeMatchedItems) {

      if (debug) {
        logFile.debug("[DROP] Removing all items specified in the item matcher, replace strategy: " + rule.replaceStrategy);
      }

      for (Iterator<ItemStack> it = currentDrops.iterator(); it.hasNext(); ) {
        ItemStack itemStack = it.next();

        for (ItemMatchEntry itemMatchEntry : rule.match.drops._drops) {

          if (itemMatchEntry.matches(itemStack, logFile, debug, "[DROP] ")) {
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

  private void addDrop(DebugFileWrapper logFile, boolean debug, List<ItemStack> newDrops, int itemQuantity, ItemStack copy) {

    int maxStackSize = copy.getMaxStackSize();

    // Check that the quantity we're supposed to drop doesn't exceed the item's
    // max stack size. If it does, drop multiple stacks.

    if (itemQuantity == 1 && copy.getCount() > 1) {
      itemQuantity = copy.getCount();
    }

    if (itemQuantity > maxStackSize) {
      int remaining = itemQuantity;

      while (remaining > maxStackSize) {
        remaining -= maxStackSize;
        ItemStack itemStack = copy.copy();
        itemStack.setCount(maxStackSize);
        newDrops.add(itemStack);
      }

      if (remaining > 0) {
        copy.setCount(remaining);
        newDrops.add(copy);
      }

    } else {
      copy.setCount(itemQuantity);
      newDrops.add(copy);
    }

    if (debug) {
      logFile.debug("[DROP] Added ItemStack to drop list: " + copy);
    }
  }

  private int getItemQuantity(int fortuneLevel, DebugFileWrapper logFile, boolean debug, List<ItemStack> originalDrops, RuleDrop ruleDrop) {

    int itemQuantity = ruleDrop.item.quantity.get(RANDOM, fortuneLevel);

    if (!ruleDrop.item.matchQuantity._drops.isEmpty()
        && !originalDrops.isEmpty()) {

      // First we count all of the dropped items.

      Map<ItemStack, Integer> countMap = new LinkedHashMap<>(originalDrops.size());

      for (ItemStack droppedItemStack : originalDrops) {
        boolean found = false;

        for (Map.Entry<ItemStack, Integer> entry : countMap.entrySet()) {
          ItemStack countStack = entry.getKey();

          if (countStack.getItem() == droppedItemStack.getItem()
              && countStack.getMetadata() == droppedItemStack.getMetadata()) {
            found = true;
            countMap.put(countStack, entry.getValue() + droppedItemStack.getCount());
            break;
          }
        }

        if (!found) {
          countMap.put(droppedItemStack.copy(), droppedItemStack.getCount());
        }
      }

      if (debug) {

        for (Map.Entry<ItemStack, Integer> entry : countMap.entrySet()) {
          logFile.debug("[DROP] Match quantity counted [" + entry.getValue() + "] of [" + entry.getKey() + "]");
        }
      }

      // Then we look for a match.

      match:
      for (Ingredient toMatch : ruleDrop.item.matchQuantity._drops) {

        for (Map.Entry<ItemStack, Integer> entry : countMap.entrySet()) {
          ItemStack candidate = entry.getKey();

          if (toMatch.apply(candidate)) {
            itemQuantity = entry.getValue();

            if (debug) {
              logFile.debug("[DROP] Selected match quantity [" + itemQuantity + "] from [" + entry.getKey() + "]");
            }

            break match;
          }
        }
      }
    }
    return itemQuantity;
  }

}
