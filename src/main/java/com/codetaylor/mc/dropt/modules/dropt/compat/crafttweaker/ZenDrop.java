package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.dropt.api.DroptAPI;
import com.codetaylor.mc.dropt.api.api.IDroptDropBuilder;
import com.codetaylor.mc.dropt.api.reference.EnumSilktouch;
import com.codetaylor.mc.dropt.api.reference.EnumXPReplaceStrategy;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@SuppressWarnings("unused")
@ZenClass("mods.dropt.Drop")
public class ZenDrop {

  private final IDroptDropBuilder drop;

  public ZenDrop() {

    this.drop = DroptAPI.drop();
  }

  @ZenMethod
  public ZenDrop selector(ZenWeight weight) {

    this.drop.selector(weight.getWeight());
    return this;
  }

  @ZenMethod
  public ZenDrop selector(ZenWeight weight, String silkTouch) {

    this.drop.selector(weight.getWeight(), EnumSilktouch.valueOf(silkTouch));
    return this;
  }

  @ZenMethod
  public ZenDrop selector(ZenWeight weight, String silkTouch, int fortuneLevelRequired) {

    this.drop.selector(weight.getWeight(), EnumSilktouch.valueOf(silkTouch), fortuneLevelRequired);
    return this;
  }

  @ZenMethod
  public ZenDrop items(IItemStack[] items) {

    this.drop.items(ZenDropt.getItemStrings(items));
    return this;
  }

  @ZenMethod
  public ZenDrop items(IItemStack[] items, ZenRange range) {

    this.drop.items(ZenDropt.getItemStrings(items), range.getRandomFortuneInt());
    return this;
  }

  @ZenMethod
  public ZenDrop xp(String replace, ZenRange amount) {

    this.drop.xp(EnumXPReplaceStrategy.valueOf(replace), amount.getRandomFortuneInt());
    return this;
  }

  public IDroptDropBuilder getDrop() {

    return this.drop;
  }
}
