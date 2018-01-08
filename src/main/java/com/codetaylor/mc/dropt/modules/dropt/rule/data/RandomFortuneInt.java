package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import java.util.Random;

public class RandomFortuneInt {

  public int fixed = 0;
  public int min = 1;
  public int max = 1;
  public int fortuneModifier = 0;

  public int get(Random random, int fortuneLevel) {

    if (this.fixed > 0) {
      return this.fixed + fortuneLevel * this.fortuneModifier;
    }

    // We need to add one here because max is inclusive. For example, if the user specifies
    // a min of 5 and a max of 5, the range would be zero and that would break the call to
    // Random#nextInt below because it needs a value > 0.
    int range = this.max - this.min + 1;

    if (range < 1) {
      return fortuneLevel * this.fortuneModifier;
    }

    return Math.max(0, random.nextInt(range) + this.min + fortuneLevel * this.fortuneModifier);
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
