package com.codetaylor.mc.dropt.modules.dropt;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import java.util.Map;
import java.util.Set;

public class ConsoleLog {

  private Set<EntityPlayer> listeningPlayers;
  private Map<String, Integer> logEntryMap;

  public ConsoleLog(
      Set<EntityPlayer> listeningPlayers,
      Map<String, Integer> logEntryMap
  ) {

    this.listeningPlayers = listeningPlayers;
    this.logEntryMap = logEntryMap;
  }

  public void addListeningPlayer(EntityPlayer player) {

    this.listeningPlayers.add(player);
  }

  public void removeListeningPlayer(EntityPlayer player) {

    this.listeningPlayers.remove(player);
  }

  public boolean hasListeningPlayer(EntityPlayer player) {

    return this.listeningPlayers.contains(player);
  }

  public boolean hasListeningPlayers() {

    return !this.listeningPlayers.isEmpty();
  }

  public void increment(ResourceLocation resourceLocation, int meta) {

    if (!this.hasListeningPlayers()) {
      return;
    }

    String key = resourceLocation + ":" + meta;
    Integer quantity = this.logEntryMap.get(key);

    if (quantity == null) {
      quantity = 0;
    }

    quantity += 1;
    this.logEntryMap.put(key, quantity);
  }

  public void update() {

    if (!this.hasListeningPlayers()) {
      return;
    }

    for (Map.Entry<String, Integer> entry : this.logEntryMap.entrySet()) {

      for (EntityPlayer player : this.listeningPlayers) {
        player.sendMessage(new TextComponentString(entry.getKey() + " [x" + entry.getValue() + "]"));
      }
    }

    this.logEntryMap.clear();
  }

}
