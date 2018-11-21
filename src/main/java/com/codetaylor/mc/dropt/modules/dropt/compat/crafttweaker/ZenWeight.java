package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.dropt.api.api.RuleDropSelectorWeight;

class ZenWeight {

  private final RuleDropSelectorWeight weight;

  /* package */ ZenWeight(RuleDropSelectorWeight weight) {

    this.weight = weight;
  }

  /* package */ RuleDropSelectorWeight getWeight() {

    return this.weight;
  }
}
