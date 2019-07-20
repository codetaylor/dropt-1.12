package com.codetaylor.mc.dropt.modules.dropt.rule.parse;

import com.codetaylor.mc.athenaeum.parser.recipe.item.MalformedRecipeItemException;
import com.codetaylor.mc.athenaeum.parser.recipe.item.ParseResult;
import com.codetaylor.mc.athenaeum.parser.recipe.item.RecipeItemParser;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.ILogger;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

public class ParserUtil {

  public static class NBTParseResult {

    private final ParseResult parseResult;
    private final NBTTagCompound tag;

    public NBTParseResult(ParseResult parseResult, @Nullable NBTTagCompound tag) {

      this.parseResult = parseResult;
      this.tag = tag;
    }

    @Nullable
    public NBTTagCompound getTag() {

      return this.tag;
    }

    public String getDomain() {

      return this.parseResult.getDomain();
    }

    public String getPath() {

      return this.parseResult.getPath();
    }

    public int getMeta() {

      return this.parseResult.getMeta();
    }

    public int getQuantity() {

      return this.parseResult.getQuantity();
    }
  }

  public static NBTParseResult parseWithNBT(String itemString, ILogger logger) throws MalformedRecipeItemException {

    NBTTagCompound tag = null;
    String[] split = itemString.split("#");
    ParseResult parse = RecipeItemParser.INSTANCE.parse(split[0]);

    if (split.length > 1) {
      // this indicates that there is an nbt tag to parse

      String nbtString = split[1];

      if (split.length > 2) {
        // this indicates that the character # was used inside the NBT string and
        // we need to stitch this back together

        StringBuilder sb = new StringBuilder(nbtString);

        for (int i = 2; i < split.length; i++) {
          sb.append("#").append(split[i]);
        }

        nbtString = sb.toString();
      }

      try {
        tag = JsonToNBT.getTagFromJson(nbtString);

      } catch (Exception e) {
        logger.error("[PARSE] Unable to parse nbt string: " + split[1]);
        throw new MalformedRecipeItemException(e);
      }
    }

    return new NBTParseResult(parse, tag);
  }

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
