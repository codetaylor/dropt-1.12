package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.athenaeum.integration.crafttweaker.mtlib.helpers.CTInputHelper;
import com.codetaylor.mc.dropt.api.DroptAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
@ZenClass("mods.dropt.Dropt")
public class ZenDropt {

  public static final Map<String, ZenRuleList> LISTS = new HashMap<>();

  @ZenMethod
  public static ZenRuleList list(String name) {

    ZenRuleList list = LISTS.get(name);

    if (list == null) {
      list = new ZenRuleList(new ResourceLocation("crafttweaker", name));
      LISTS.put(name, list);
    }

    return list;
  }

  @ZenMethod
  public static ZenRule rule() {

    return new ZenRule();
  }

  @ZenMethod
  public static ZenHarvester harvester() {

    return new ZenHarvester();
  }

  @ZenMethod
  public static ZenDrop drop() {

    return new ZenDrop();
  }

  @ZenMethod
  public static ZenRange range(int fixed) {

    return new ZenRange(DroptAPI.range(fixed));
  }

  @ZenMethod
  public static ZenRange range(int min, int max) {

    return new ZenRange(DroptAPI.range(min, max));
  }

  @ZenMethod
  public static ZenRange range(int min, int max, int fortuneModifier) {

    return new ZenRange(DroptAPI.range(min, max, fortuneModifier));
  }

  @ZenMethod
  public static ZenWeight weight(int value) {

    return new ZenWeight(DroptAPI.weight(value));
  }

  @ZenMethod
  public static ZenWeight weight(int value, int fortuneModifier) {

    return new ZenWeight(DroptAPI.weight(value, fortuneModifier));
  }

  public static String[] getItemStrings(IItemStack[] items) {

    ItemStack[] itemStacks = CTInputHelper.toStacks(items);
    String[] itemStrings = new String[itemStacks.length];

    for (int i = 0; i < itemStacks.length; i++) {
      itemStrings[i] = DroptAPI.itemString(itemStacks[i]);
    }
    return itemStrings;
  }

  /* package */
  static String[] getItemStrings(IIngredient[] items) {

    ArrayList<ItemStack> itemStacks = new ArrayList<>();

    for (int i = 0; i < items.length; i++) {
      CTInputHelper.getMatchingStacks(items[i], itemStacks);
    }

    String[] itemStrings = new String[itemStacks.size()];

    for (int i = 0; i < itemStacks.size(); i++) {
      itemStrings[i] = DroptAPI.itemString(itemStacks.get(i));
    }
    return itemStrings;
  }
}
