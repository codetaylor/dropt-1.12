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
@ZenDocClass(value = "mods.dropt.Dropt", description = {
    "This class acts as an entry point to the Dropt API.",
    "",
    "Each method returns either a ready-to-configure object or a pre-configured object using the given parameters."
})
@ZenClass("mods.dropt.Dropt")
public class ZenDropt {

  public static final Map<String, ZenRuleList> LISTS = new HashMap<>();

  @ZenDocMethod(
      order = 1,
      description = {
          "Returns a new, ready-to-configure list object.",
          "Subsequent calls with the same name will return the same list object.",
          "@see /json/syntax/#irulelist"
      },
      args = {"name"}
  )
  @ZenMethod
  public static ZenRuleList list(String name) {

    ZenRuleList list = LISTS.get(name);

    if (list == null) {
      list = new ZenRuleList(new ResourceLocation("crafttweaker", name));
      LISTS.put(name, list);
    }

    return list;
  }

  @ZenDocMethod(order = 2,
      description = {
          "Returns a new, ready-to-configure Rule object.",
          "@see /json/syntax/#irule"
      })
  @ZenMethod
  public static ZenRule rule() {

    return new ZenRule();
  }

  @ZenDocMethod(order = 3,
      description = {
          "Returns a new, ready-to-configure Harvester object.",
          "@see /json/syntax/#irulematchharvester"
      })
  @ZenMethod
  public static ZenHarvester harvester() {

    return new ZenHarvester();
  }

  @ZenDocMethod(order = 4,
      description = {
          "Returns a new, ready-to-configure Drop object.",
          "@see /json/syntax/#iruledrop"
      })
  @ZenMethod
  public static ZenDrop drop() {

    return new ZenDrop();
  }

  @ZenDocMethod(order = 5,
      description = {
          "Returns a range object with a fixed value.",
          "@see /json/syntax/#irangeint"
      },
      args = {"fixed"})
  @ZenMethod
  public static ZenRange range(int fixed) {

    return new ZenRange(DroptAPI.range(fixed));
  }

  @ZenDocMethod(order = 6,
      description = {
          "Returns a range object with a minimum and maximum value.",
          "",
          "The minimum and maximum values are inclusive.",
          "@see /json/syntax/#irangeint"
      },
      args = {"min", "max"})
  @ZenMethod
  public static ZenRange range(int min, int max) {

    return new ZenRange(DroptAPI.range(min, max));
  }

  @ZenDocMethod(order = 7,
      description = {
          "Returns a range object with the given minimum, maximum, and fortune modifier value.",
          "",
          "The minimum and maximum values are inclusive.",
          "@see /json/syntax/#irandomfortuneint"
      },
      args = {"min", "max", "fortuneModifier"})
  @ZenMethod
  public static ZenRange range(int min, int max, int fortuneModifier) {

    return new ZenRange(DroptAPI.range(min, max, fortuneModifier));
  }

  @ZenDocMethod(order = 8,
      description = {
          "Returns a weight object with the given weight.",
          "@see /json/syntax/#iruledropselectorweight"
      },
      args = {"weight"})
  @ZenMethod
  public static ZenWeight weight(int weight) {

    return new ZenWeight(DroptAPI.weight(weight));
  }

  @ZenDocMethod(order = 9,
      description = {
          "Returns a weight object with the given weight and fortune modifier.",
          "@see /json/syntax/#iruledropselectorweight"
      },
      args = {"weight", "fortuneModifier"})
  @ZenMethod
  public static ZenWeight weight(int weight, int fortuneModifier) {

    return new ZenWeight(DroptAPI.weight(weight, fortuneModifier));
  }

  /* package */
  static String[] getItemStrings(IItemStack[] items) {

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
