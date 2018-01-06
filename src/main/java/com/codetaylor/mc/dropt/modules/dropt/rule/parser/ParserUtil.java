package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;

public class ParserUtil {

  public static void addSubItemsToList(Item item, List<ItemStack> list) {

    NonNullList<ItemStack> items = NonNullList.create();
    item.getSubItems(CreativeTabs.SEARCH, items);

    for (ItemStack subItem : items) {
      list.add(new ItemStack(subItem.getItem(), 1, subItem.getMetadata()));
    }
  }

  private ParserUtil() {
    //
  }

}
