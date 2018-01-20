package com.codetaylor.mc.dropt.modules.dropt.events;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.ModuleDroptConfig;
import com.codetaylor.mc.dropt.modules.dropt.rule.RuleLocator;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.drop.DropModifier;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

  private RuleLocator ruleLocator;
  private DropModifier dropModifier;
  private DebugFileWrapper debugFileWrapper;

  public EventHandler(
      RuleLocator ruleLocator,
      DropModifier dropModifier
  ) {

    this.ruleLocator = ruleLocator;
    this.dropModifier = dropModifier;
  }

  @SubscribeEvent
  public void onHarvestDropsEvent(BlockEvent.HarvestDropsEvent event) {

    Rule matchedRule = this.ruleLocator.locate(event);

    if (matchedRule != null) {
      long start = System.currentTimeMillis();

      if (matchedRule.debug) {
        this.initializeDebugFileWrapper();
      }

      this.dropModifier.modifyDrops(
          event.getWorld(),
          event.getPos(),
          matchedRule,
          event.getDrops(),
          event.isSilkTouching(),
          event.getFortuneLevel(),
          this.debugFileWrapper,
          matchedRule.debug
      );

      if (ModuleDroptConfig.ENABLE_PROFILE_LOG_OUTPUT) {

        this.initializeDebugFileWrapper();
        this.debugFileWrapper.info(String.format(
            "Modified drops in %d ms",
            (System.currentTimeMillis() - start)
        ));
      }
    }

    this.closeDebugFileWrapper();
  }

  private void initializeDebugFileWrapper() {

    if (this.debugFileWrapper == null) {
      this.debugFileWrapper = new DebugFileWrapper(ModuleDropt.LOG_FILE_WRITER_PROVIDER.createLogFileWriter());
    }
  }

  private void closeDebugFileWrapper() {

    if (this.debugFileWrapper != null) {
      this.debugFileWrapper.close();
      this.debugFileWrapper = null;
    }
  }

}
