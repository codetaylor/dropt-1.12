package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import java.util.Random;

public class RandomFortuneInt {

  public int fixed;
  public int min;
  public int max;
  public int fortuneModifier;

  public RandomFortuneInt() {
    //
  }

  public RandomFortuneInt(int fixed) {

    this.fixed = fixed;
  }

  public int get(Random random, int fortuneLevel) {

    if (this.fixed > 0) {
      return this.fixed + Math.max(0, fortuneLevel * this.fortuneModifier);
    }

    // Add one to the range to make the upper bound inclusive.
    int range = Math.abs(this.max - this.min) + 1;
    return random.nextInt(range) + Math.max(0, this.min + fortuneLevel * this.fortuneModifier);
  }

  @Override
  public String toString() {

    return "RandomFortuneInt{" +
        "fixed=" + fixed +
        ", min=" + min +
        ", max=" + max +
        ", fortuneModifier=" + fortuneModifier +
        '}';
  }
}
