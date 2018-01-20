package com.codetaylor.mc.dropt.modules.dropt.rule.parse;

import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;

public class ParserUtil {

  public static void addSubItemsToList(
      Item item,
      List<ItemStack> list,
      DebugFileWrapper debugFileWrapper,
      boolean debug
  ) {

    NonNullList<ItemStack> tempList = NonNullList.create();
    item.getSubItems(CreativeTabs.SEARCH, tempList);

    for (ItemStack subItem : tempList) {
      ItemStack itemStack = new ItemStack(subItem.getItem(), 1, subItem.getMetadata());
      list.add(itemStack);

      if (debug) {
        debugFileWrapper.debug("[PARSE] Added itemStack to drop: " + itemStack);
      }
    }
  }

  private ParserUtil() {
    //
  }

}
