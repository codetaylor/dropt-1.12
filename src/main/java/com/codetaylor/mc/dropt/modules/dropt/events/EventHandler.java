package com.codetaylor.mc.dropt.modules.dropt.events;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.ModuleDroptConfig;
import com.codetaylor.mc.dropt.modules.dropt.rule.RuleLocator;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.drop.DropModifier;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.ExperienceCache;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.HeldItemCache;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

public class EventHandler {

  private RuleLocator ruleLocator;
  private DropModifier dropModifier;
  private DebugFileWrapper debugFileWrapper;

  private HeldItemCache heldItemCache;
  private ExperienceCache experienceCache;

  public EventHandler(
      RuleLocator ruleLocator,
      DropModifier dropModifier,
      HeldItemCache heldItemCache,
      ExperienceCache experienceCache
  ) {

    this.ruleLocator = ruleLocator;
    this.dropModifier = dropModifier;
    this.heldItemCache = heldItemCache;
    this.experienceCache = experienceCache;
  }

  @SubscribeEvent
  public void onTickEvent(TickEvent event) {

    if (event.side == Side.SERVER) {
      ModuleDropt.CONSOLE_LOG.update();
    }

  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
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

    this.experienceCache.put(player.getName(), event.getExpToDrop());

    event.setExpToDrop(0);
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void onHarvestDropsEvent(BlockEvent.HarvestDropsEvent event) {

    IBlockState blockState = event.getState();
    Block block = blockState.getBlock();
    int meta = block.getMetaFromState(blockState);
    ResourceLocation registryName = block.getRegistryName();
    World world = event.getWorld();

    if (registryName != null) {
      ModuleDropt.CONSOLE_LOG.increment(registryName, meta);
    }

    Rule matchedRule = this.ruleLocator.locate(
        world,
        event.getHarvester(),
        event.getPos(),
        blockState,
        event.getDrops(),
        this.heldItemCache
    );

    int experience = 0;

    if (event.getHarvester() != null) {
      experience = this.experienceCache.get(event.getHarvester().getName());
    }

    if (matchedRule != null) {
      long start = System.currentTimeMillis();

      if (matchedRule.debug) {
        this.initializeDebugFileWrapper();
      }

      this.dropModifier.modifyDrops(
          world,
          event.getPos(),
          matchedRule,
          event.getDrops(),
          this.isSilkTouching(event, this.heldItemCache),
          event.getFortuneLevel(),
          experience,
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

    } else {

      while (experience > 0) {
        int xpDrop = EntityXPOrb.getXPSplit(experience);
        experience -= xpDrop;
        world.spawnEntity(new EntityXPOrb(
            world,
            event.getPos().getX(),
            event.getPos().getY() + 0.5,
            event.getPos().getZ(),
            xpDrop
        ));
      }
    }

    this.closeDebugFileWrapper();
  }

  private boolean isSilkTouching(BlockEvent.HarvestDropsEvent event, HeldItemCache heldItemCache) {

    if (event.isSilkTouching()) {
      return true;
    }

    EntityPlayer harvester = event.getHarvester();

    if (harvester == null) {
      return false;
    }

    ItemStack itemStack = heldItemCache.get(harvester.getName());

    if (itemStack.isEmpty()) {
      return false;
    }

    Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);

    return enchantments.containsKey(Enchantments.SILK_TOUCH);
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
