package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.dropt.api.api.RandomFortuneInt;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.dropt.Range")
class ZenRange {

  private final RandomFortuneInt randomFortuneInt;

  /* package */ ZenRange(RandomFortuneInt randomFortuneInt) {

    this.randomFortuneInt = randomFortuneInt;
  }

  /* package */ RandomFortuneInt getRandomFortuneInt() {

    return this.randomFortuneInt;
  }
}
