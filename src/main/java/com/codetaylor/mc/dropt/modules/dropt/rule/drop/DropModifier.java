package com.codetaylor.mc.dropt.modules.dropt.rule.drop;

import com.codetaylor.mc.athenaeum.util.WeightedPicker;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.*;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.ItemMatchEntry;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DropModifier {

  private static final Random RANDOM = new Random();

  public List<ItemStack> modifyDrops(
      World world,
      BlockPos pos,
      Rule rule,
      List<ItemStack> currentDrops,
      boolean isSilkTouching,
      int fortuneLevel,
      DebugFileWrapper logFile,
      boolean debug
  ) {

    if (debug) {
      logFile.debug("");
    }

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

    for (RuleDrop drop : rule.drops) {

      if (drop.selector.isValidCandidate(isSilkTouching, fortuneLevel, logFile, debug)) {
        int weight = drop.selector.weight.value + (fortuneLevel * drop.selector.weight.fortuneModifier);
        picker.add(weight, drop);
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

    int dropCount = rule.dropCount.get(RANDOM, fortuneLevel);

    if (debug) {
      logFile.debug("[DROP] Drop count: " + dropCount);
    }

    List<ItemStack> newDrops = new ArrayList<>();

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

      int itemQuantity = ruleDrop.item.quantity.get(RANDOM, fortuneLevel);

      if (debug) {
        logFile.debug("[DROP] Selected drop: " + ruleDrop.toString());
        logFile.debug("[DROP] Selected drop quantity: " + itemQuantity);
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

      ItemStack copy = ruleDrop.item._items.get(RANDOM.nextInt(ruleDrop.item._items.size())).copy();
      copy.setCount(itemQuantity);
      newDrops.add(copy);

      if (debug) {
        logFile.debug("[DROP] Added ItemStack to drop list: " + copy);
      }

      int xp = ruleDrop.xp.get(RANDOM, fortuneLevel);

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

}
