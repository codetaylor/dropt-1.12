package com.codetaylor.mc.dropt.modules.dropt.rule;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleDrop;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.LogFileWrapper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;

public class ProfileUtil {

  public static void injectRules(List<RuleList> ruleLists, LogFileWrapper logFileWriter) {

    int ruleCount = 0;
    long start = System.currentTimeMillis();

    RuleList ruleList = new RuleList();
    ruleLists.add(ruleList);

    for (Block block : ForgeRegistries.BLOCKS.getValues()) {

      if (block == Blocks.STONE || block == Blocks.AIR) {
        continue;
      }

      for (Item item : ForgeRegistries.ITEMS.getValues()) {

        Rule rule = new Rule();
        ruleList.rules.add(rule);
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

    if (registryName != null) {
      rule.match.blocks.blocks = new String[]{registryName.toString()};
      rule.match.harvester.heldItemMainHand.items = new String[]{"minecraft:stone_pickaxe:*"};

      RuleDrop ruleDrop = new RuleDrop();
      rule.drops = new RuleDrop[]{ruleDrop};
      ruleDrop.item.items = new String[]{"minecraft:string"};
    }

    logFileWriter.info(String.format(
        "Injected %d rules in %d ms",
        ruleCount + 1,
        (System.currentTimeMillis() - start)
    ));

  }
}
