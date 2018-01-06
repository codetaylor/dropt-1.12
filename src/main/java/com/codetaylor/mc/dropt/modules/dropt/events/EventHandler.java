package com.codetaylor.mc.dropt.modules.dropt.events;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EventHandler {

  @SubscribeEvent
  public void onHarvestDropsEvent(BlockEvent.HarvestDropsEvent event) {

    Rule matchedRule = null;

    for (RuleList ruleList : ModuleDropt.RULE_LISTS) {

      for (Rule rule : ruleList.rules) {

        if (rule.match.matches(event)) {
          matchedRule = rule;
          break;
        }
      }
    }

    if (matchedRule != null) {
      List<ItemStack> drops = event.getDrops();
      boolean silkTouching = event.isSilkTouching();
      int fortuneLevel = event.getFortuneLevel();
      matchedRule.modifyDrops(drops, silkTouching, fortuneLevel);
    }
  }

}
