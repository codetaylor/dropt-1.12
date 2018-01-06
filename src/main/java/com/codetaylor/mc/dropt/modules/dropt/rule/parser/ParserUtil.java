package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;

public class ParserUtil {

  public static void addSubItemsToList(Item item, List<ItemStack> list) {

    NonNullList<ItemStack> tempList = NonNullList.create();
    item.getSubItems(CreativeTabs.SEARCH, tempList);

    for (ItemStack subItem : tempList) {
      list.add(new ItemStack(subItem.getItem(), 1, subItem.getMetadata()));
    }
  }

  private ParserUtil() {
    //
  }

}
