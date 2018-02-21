package com.codetaylor.mc.dropt.modules.dropt.events;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.ModuleDroptConfig;
import com.codetaylor.mc.dropt.modules.dropt.rule.RuleLocator;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.drop.DropModifier;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.HeldItemCache;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

  private RuleLocator ruleLocator;
  private DropModifier dropModifier;
  private DebugFileWrapper debugFileWrapper;

  private HeldItemCache heldItemCache;

  public EventHandler(
      RuleLocator ruleLocator,
      DropModifier dropModifier,
      HeldItemCache heldItemCache
  ) {

    this.ruleLocator = ruleLocator;
    this.dropModifier = dropModifier;
    this.heldItemCache = heldItemCache;
  }

  @SubscribeEvent
  public void onBlockBreakEvent(BlockEvent.BreakEvent event) {

    EntityPlayer player = event.getPlayer();

    if (player instanceof FakePlayer) {
      return;
    }

    // Cache a copy of the player's held item before damage is applied to the item.
    // This prevents the case when the player's item breaks before the harvest
    // event is processed, resulting in an incorrect rule match.
    ItemStack heldItemMainhand = player.getHeldItemMainhand();

    if (heldItemMainhand.isEmpty()) {
      this.heldItemCache.put(player.getName(), ItemStack.EMPTY);

    } else {
      this.heldItemCache.put(player.getName(), heldItemMainhand.copy());
    }
  }

  @SubscribeEvent
  public void onHarvestDropsEvent(BlockEvent.HarvestDropsEvent event) {

    Rule matchedRule = this.ruleLocator.locate(event, this.heldItemCache);

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
