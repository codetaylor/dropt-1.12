package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.dropt.api.DroptAPI;
import com.codetaylor.mc.dropt.api.api.IDroptDropBuilder;
import com.codetaylor.mc.dropt.api.reference.EnumDropListStrategy;
import com.codetaylor.mc.dropt.api.reference.EnumSilktouch;
import com.codetaylor.mc.dropt.api.reference.EnumXPReplaceStrategy;
import com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker.export.ZenDocClass;
import com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker.export.ZenDocMethod;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Map;

@SuppressWarnings("unused")
@ZenDocClass(
    value = "mods.dropt.Drop",
    description = {
        "@see /json/syntax/#iruledrop"
    }
)
@ZenClass("mods.dropt.Drop")
public class ZenDrop {

  private final IDroptDropBuilder drop;

  public ZenDrop() {

    this.drop = DroptAPI.drop();
  }

  @ZenDocMethod(
      order = 0,
      description = {
          "Forces a drop to always drop, ignores selector.",
          "@see /json/syntax/#iruledrop"
      }
  )
  @ZenMethod
  public ZenDrop force() {

    this.drop.force();
    return this;
  }

  @ZenDocMethod(
      order = 1,
      description = {
          "Defines a selector for this drop.",
          "@see /json/syntax/#iruledropselector"
      },
      args = {"weight"}
  )
  @ZenMethod
  public ZenDrop selector(ZenWeight weight) {

    this.drop.selector(weight.getWeight());
    return this;
  }

  @ZenDocMethod(
      order = 2,
      description = {
          "Defines a selector for this drop.",
          "@see /json/syntax/#iruledropselector"
      },
      args = {"weight", "silkTouch"}
  )
  @ZenMethod
  public ZenDrop selector(ZenWeight weight, String silkTouch) {

    this.drop.selector(weight.getWeight(), EnumSilktouch.valueOf(silkTouch));
    return this;
  }

  @ZenDocMethod(
      order = 3,
      description = {
          "Defines a selector for this drop.",
          "@see /json/syntax/#iruledropselector"
      },
      args = {"weight", "silkTouch", "fortuneLevelRequired"}
  )
  @ZenMethod
  public ZenDrop selector(ZenWeight weight, String silkTouch, int fortuneLevelRequired) {

    this.drop.selector(weight.getWeight(), EnumSilktouch.valueOf(silkTouch), fortuneLevelRequired);
    return this;
  }

  @ZenDocMethod(
      order = 4,
      description = {
          "Defines the item list for this drop.",
          "@see /json/syntax/#iruledropitem"
      },
      args = {"items"}
  )
  @ZenMethod
  public ZenDrop items(IItemStack[] items) {

    this.drop.items(ZenDropt.getItemStrings(items));
    return this;
  }

  @ZenDocMethod(
      order = 5,
      description = {
          "Defines the item list for this drop.",
          "@see /json/syntax/#iruledropitem"
      },
      args = {"drop", "items"}
  )
  @ZenMethod
  public ZenDrop items(String drop, IItemStack[] items) {

    this.drop.items(EnumDropListStrategy.valueOf(drop), ZenDropt.getItemStrings(items));
    return this;
  }

  @ZenDocMethod(
      order = 6,
      description = {
          "Defines the item list for this drop.",
          "@see /json/syntax/#iruledropitem"
      },
      args = {"items", "range"}
  )
  @ZenMethod
  public ZenDrop items(IItemStack[] items, ZenRange range) {

    this.drop.items(ZenDropt.getItemStrings(items), range.getRandomFortuneInt());
    return this;
  }

  @ZenDocMethod(
      order = 7,
      description = {
          "Defines the item list for this drop.",
          "@see /json/syntax/#iruledropitem"
      },
      args = {"drop", "items", "range"}
  )
  @ZenMethod
  public ZenDrop items(String drop, IItemStack[] items, ZenRange range) {

    this.drop.items(EnumDropListStrategy.valueOf(drop), ZenDropt.getItemStrings(items), range.getRandomFortuneInt());
    return this;
  }

  @ZenDocMethod(
      order = 8,
      description = {
          "Defines drops to match quantity.",
          "@see /json/syntax/#iruledrop"
      },
      args = {"drops"}
  )
  @ZenMethod
  public ZenDrop matchQuantity(IItemStack[] drops) {

    this.drop.matchQuantity(ZenDropt.getItemStrings(drops));
    return this;
  }

  @ZenDocMethod(
      order = 9,
      description = {
          "Defines an experience drop.",
          "@see /json/syntax/#iruledrop"
      },
      args = {"replace", "amount"}
  )
  @ZenMethod
  public ZenDrop xp(String replace, ZenRange amount) {

    this.drop.xp(EnumXPReplaceStrategy.valueOf(replace), amount.getRandomFortuneInt());
    return this;
  }

  @ZenDocMethod(
      order = 10,
      description = {
          "Defines a blockstate to replace the block with.",
          "@see /json/syntax/#iruledrop"
      },
      args = {"block", "properties"}
  )
  @ZenMethod
  public ZenDrop replaceBlock(String block, Map<String, String> properties) {

    this.drop.replaceBlock(block, properties);
    return this;
  }

  public IDroptDropBuilder getDrop() {

    return this.drop;
  }
}
