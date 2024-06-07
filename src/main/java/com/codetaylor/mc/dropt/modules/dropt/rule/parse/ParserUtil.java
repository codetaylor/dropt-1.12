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

    @Override
    public String toString() {

      return this.getDomain() + ":" + this.getPath() + ":" + this.getMeta() + ((this.tag != null) ? "#" + this.tag : "") + " * " + this.getQuantity();
    }
  }

  public static NBTParseResult parseWithNBT(String itemString, ILogger logger) throws MalformedRecipeItemException {

    NBTTagCompound tag = null;
    String[] split = itemString.split("#");
    String actualItemString = split[0];

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

      String[] nbtSplit = nbtString.split("\\*");

      if (nbtSplit.length > 1) {
        // this indicates that there is either a * in the NBT or at the end
        // of the string to indicate quantity

        int nbtSplitLength = nbtSplit.length;

        try {
          // if this doesn't throw, it's likely that there's a quantity
          int quantity = Integer.parseInt(nbtSplit[nbtSplit.length - 1].trim());
          actualItemString += " * " + quantity;
          nbtSplitLength -= 1;
        } catch (NumberFormatException ignore) {
          // ignored
        }

        // stitch the NBT back together

        StringBuilder sb = new StringBuilder(nbtSplit[0]);

        for (int i = 1; i < nbtSplitLength; i++) {
          sb.append("*").append(nbtSplit[i]);
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

    ParseResult parse = RecipeItemParser.INSTANCE.parse(actualItemString);
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

  public static void main(String[] args) {

    ILogger logger = new ILogger() {

      @Override
      public void warn(String message) {

        System.out.println(message);
      }

      @Override
      public void error(String message) {

        System.out.println(message);
      }

      @Override
      public void error(String message, Throwable error) {

        System.out.println(message);
        System.out.println(error.getMessage());
        error.printStackTrace();
      }
    };

    String[] test = {
        "minecraft:cobblestone:0#{display:{Name:\"Never * 73     # ** blah\"}}   *    42  ",
        "minecraft:cobblestone:0#{display:{Name:\"Never * 73     # ** blah\"}} * 42",
        "minecraft:cobblestone:0   *    42  ",
        "minecraft:cobblestone:0 * 42"
    };

    for (int i = 0; i < test.length; i+=2) {
      String s = test[i];

      try {
        NBTParseResult result = ParserUtil.parseWithNBT(s, logger);

        if (!result.toString().equals(test[i + 1])) {
          System.out.println(result + " != " + test[i + 1]);
        }

      } catch (MalformedRecipeItemException e) {
        e.printStackTrace();
        break;
      }
    }
  }

}
