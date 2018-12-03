package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.dropt.api.DroptAPI;
import com.codetaylor.mc.dropt.api.api.IDroptDropBuilder;
import com.codetaylor.mc.dropt.api.reference.EnumSilktouch;
import com.codetaylor.mc.dropt.api.reference.EnumXPReplaceStrategy;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@SuppressWarnings("unused")
@ZenDocClass(
    value = "mods.dropt.Drop",
    description = {
        "@see json/syntax/#iruledrop"
    }
)
@ZenClass("mods.dropt.Drop")
public class ZenDrop {

  private final IDroptDropBuilder drop;

  public ZenDrop() {

    this.drop = DroptAPI.drop();
  }

  @ZenDocMethod(
      order = 1,
      description = {
          "Defines a selector for this drop.",
          "@see json/syntax/#iruledropselector"
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
          "@see json/syntax/#iruledropselector"
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
          "@see json/syntax/#iruledropselector"
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
          "@see json/syntax/#iruledropitem"
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
          "@see json/syntax/#iruledropitem"
      },
      args = {"items", "range"}
  )
  @ZenMethod
  public ZenDrop items(IItemStack[] items, ZenRange range) {

    this.drop.items(ZenDropt.getItemStrings(items), range.getRandomFortuneInt());
    return this;
  }

  @ZenDocMethod(
      order = 6,
      description = {
          "Defines an experience drop.",
          "@see json/syntax/#iruledrop"
      },
      args = {"replace", "amount"}
  )
  @ZenMethod
  public ZenDrop xp(String replace, ZenRange amount) {

    this.drop.xp(EnumXPReplaceStrategy.valueOf(replace), amount.getRandomFortuneInt());
    return this;
  }

  public IDroptDropBuilder getDrop() {

    return this.drop;
  }
}
