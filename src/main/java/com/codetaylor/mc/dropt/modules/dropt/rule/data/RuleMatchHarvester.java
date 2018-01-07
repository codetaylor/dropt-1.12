package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RuleMatchHarvester {

  public transient List<ItemStack> _heldItemMainHand = new ArrayList<>();

  public EnumHarvesterType type = EnumHarvesterType.ANY;
  public RuleMatchHarvesterGameStage gamestages = new RuleMatchHarvesterGameStage();
  public String[] heldItemMainHand = new String[0];
  public String[] playerName = new String[0];

  public boolean matches(@Nullable EntityPlayer harvester) {

    if (this.type == EnumHarvesterType.ANY) {

      if (harvester != null) {
        return this.checkHeldItemMainHand(harvester.getHeldItemMainhand())
            && this.checkPlayerName(harvester.getName())
            && this.gamestages.matches(harvester);
      }

    } else if (this.type == EnumHarvesterType.NON_PLAYER) {

      return (harvester == null);

    } else if (this.type == EnumHarvesterType.PLAYER) {

      return (harvester != null
          && this.checkHeldItemMainHand(harvester.getHeldItemMainhand())
          && this.checkPlayerName(harvester.getName())
          && this.gamestages.matches(harvester));
    }

    return false;
  }

  private boolean checkPlayerName(String playerName) {

    if (this.playerName.length == 0) {
      return true;
    }

    for (String matchName : this.playerName) {

      if (matchName.equals(playerName)) {
        return true;
      }
    }

    return false;
  }

  private boolean checkHeldItemMainHand(ItemStack heldItemStack) {

    if (this._heldItemMainHand.isEmpty()) {
      return true;
    }

    for (ItemStack itemStack : this._heldItemMainHand) {
      Item heldItem = heldItemStack.getItem();
      int metadata = heldItemStack.getMetadata();

      if ((itemStack.getItem() == heldItem)
          && ((itemStack.getMetadata() == OreDictionary.WILDCARD_VALUE) || (itemStack.getMetadata() == metadata))) {
        return true;
      }
    }

    return false;
  }
}
