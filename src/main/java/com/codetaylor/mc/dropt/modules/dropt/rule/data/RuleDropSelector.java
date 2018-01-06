package com.codetaylor.mc.dropt.modules.dropt.rule.data;

public class RuleDropSelector {

  public RuleDropSelectorWeight weight = new RuleDropSelectorWeight();
  public EnumSilktouch silktouch = EnumSilktouch.ANY;
  public int fortuneLevelRequired = 0;

  public boolean isValidCandidate(boolean isSilkTouching, int fortuneLevel) {

    if (fortuneLevel < this.fortuneLevelRequired) {
      return false;
    }

    if (this.silktouch == EnumSilktouch.REQUIRED
        && !isSilkTouching) {
      return false;
    }

    if (this.silktouch == EnumSilktouch.EXCLUDED
        && isSilkTouching) {
      return false;
    }

    return true;
  }
}
