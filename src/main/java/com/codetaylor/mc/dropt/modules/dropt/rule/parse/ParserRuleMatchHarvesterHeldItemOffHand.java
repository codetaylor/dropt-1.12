package com.codetaylor.mc.dropt.modules.dropt.rule.parse;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleMatchHarvesterHeldItem;

public class ParserRuleMatchHarvesterHeldItemOffHand
    extends ParserRuleMatchHarvesterHeldItem {

  @Override
  protected RuleMatchHarvesterHeldItem getHeldItemData(Rule rule) {

    return rule.match.harvester.heldItemOffHand;
  }
}
