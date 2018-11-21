package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.dropt.api.DroptAPI;
import com.codetaylor.mc.dropt.api.event.DroptLoadRulesEvent;
import crafttweaker.mc1120.events.ActionApplyEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CTCompatEventHandler {

  @SubscribeEvent
  public void on(DroptLoadRulesEvent event) {

    // This ensures that rules defined in CT scripts get reloaded.

    for (ZenRuleList list : ZenDropt.LISTS.values()) {
      DroptAPI.registerRuleList(list.getResourceLocation(), list.getPriority(), list.getRuleList());
    }
  }

  @SubscribeEvent
  public void on(ActionApplyEvent.Post event) {

    // This ensures that rules defined in CT scripts get loaded the first time.

    for (ZenRuleList list : ZenDropt.LISTS.values()) {
      DroptAPI.registerRuleList(list.getResourceLocation(), list.getPriority(), list.getRuleList());
    }
  }

}
