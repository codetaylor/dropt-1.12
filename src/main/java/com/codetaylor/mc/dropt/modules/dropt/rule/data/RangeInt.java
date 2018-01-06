package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import java.util.Random;

public class RangeInt {

  public int min = 0;
  public int max = 0;
  public int fortuneModifier = 0;

  public int get(Random random, int fortuneLevel) {

    int range = this.max - this.min;

    if (range < 1) {
      return fortuneLevel * this.fortuneModifier;
    }

    return Math.max(0, random.nextInt(range) + this.min + fortuneLevel * this.fortuneModifier);
  }

}
