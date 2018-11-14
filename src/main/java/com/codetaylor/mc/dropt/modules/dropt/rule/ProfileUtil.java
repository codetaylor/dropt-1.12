package com.codetaylor.mc.dropt.modules.dropt.rule;

import com.codetaylor.mc.dropt.api.api.RuleDropSelectorWeight;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.*;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ProfileUtil {

  public static void injectRules(List<RuleList> ruleLists, DebugFileWrapper logFileWriter) {

    int ruleCount = 0;
    long start = System.currentTimeMillis();

    RuleList ruleList = new RuleList();
    ruleLists.add(ruleList);

    int sanity = 150000;

    sanity:
    for (Block block : ForgeRegistries.BLOCKS.getValues()) {

      if (block == Blocks.STONE || block == Blocks.AIR) {
        continue;
      }

      for (Item item : ForgeRegistries.ITEMS.getValues()) {

        if (sanity <= 0) {
          break sanity;
        }

        Rule rule = new Rule();
        ruleList.rules.add(rule);
        sanity -= 1;
        ruleCount += 1;

        ResourceLocation registryName = block.getRegistryName();

        if (registryName != null) {
          rule.match.blocks.blocks = new String[]{registryName.toString()};
          rule.match.harvester.heldItemMainHand.items = new String[]{item.getRegistryName().toString()};

          RuleDrop ruleDrop = new RuleDrop();
          rule.drops = new RuleDrop[]{ruleDrop};
          ruleDrop.item.items = new String[]{Blocks.STONE.getRegistryName().toString()};
        }

      }
    }

    Rule rule = new Rule();
    //rule.debug = true;
    ruleList.rules.add(rule);

    ResourceLocation registryName = Blocks.STONE.getRegistryName();

    int selectorCount = 0;

    if (registryName != null) {
      rule.match.blocks.blocks = new String[]{registryName.toString()};
      rule.match.harvester.heldItemMainHand.items = new String[]{"minecraft:stone_pickaxe:*"};

      List<RuleDrop> ruleDropList = new ArrayList<>();

      for (Item item : ForgeRegistries.ITEMS.getValues()) {
        RuleDrop ruleDrop = new RuleDrop();
        ruleDrop.item.items = new String[]{item.getRegistryName().toString()};
        ruleDrop.selector = new RuleDropSelector();
        ruleDrop.selector.weight = new RuleDropSelectorWeight();
        ruleDrop.selector.weight.value = 10;
        ruleDropList.add(ruleDrop);
        selectorCount += 1;
      }

      rule.drops = ruleDropList.toArray(new RuleDrop[ruleDropList.size()]);
    }

    logFileWriter.info(String.format(
        "Injected %d rules in %d ms",
        ruleCount + 1,
        (System.currentTimeMillis() - start)
    ));

    logFileWriter.info(String.format(
        "Test rule has %d weighted selectors",
        selectorCount
    ));

  }
}
