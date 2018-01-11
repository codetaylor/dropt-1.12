package com.codetaylor.mc.dropt.modules.dropt.rule.parse;

import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RecipeItemParser {

  @Nonnull
  public ParseResult parse(@Nullable String data) throws MalformedRecipeItemException {

    if (data == null || "null".equals(data.trim())) {
      return ParseResult.NULL;
    }

    ParseResult result = new ParseResult();
    result.setMeta(0);
    result.setQuantity(1);

    String[] split = data.split(":");

    if (split.length < 2 || split.length > 3) {
      throw new MalformedRecipeItemException(String.format(
          "[PARSE] Too many segments in [%s], must be two or three segments: <domain:path> or <domain:path:meta>",
          data
      ));
    }

    result.setDomain(split[0].trim());

    String[] pathSplit = split[1].split("\\*");

    result.setPath(pathSplit[0].trim());

    if (pathSplit.length > 1) {

      try {
        result.setQuantity(Integer.valueOf(pathSplit[1].trim()));

      } catch (NumberFormatException e) {
        throw new MalformedRecipeItemException(String.format("[PARSE] Expected integer, got [%s]", split[1].trim()));
      }
    }

    if (split.length == 3) {
      String meta = split[2].trim();

      if ("*".equals(meta.substring(0, 1))) {
        result.setMeta(OreDictionary.WILDCARD_VALUE);
        String quantity = meta.replace("*", "").trim();

        if (!quantity.isEmpty()) {
          try {
            result.setQuantity(Integer.valueOf(quantity));

          } catch (NumberFormatException e) {
            throw new MalformedRecipeItemException(String.format("[PARSE] Expected integer, got [%s]", quantity));
          }
        }

      } else {
        String[] metaSplit = meta.split("\\*");

        try {
          result.setMeta(Integer.valueOf(metaSplit[0].trim()));

        } catch (NumberFormatException e) {
          throw new MalformedRecipeItemException(String.format("[PARSE] Expected integer, got [%s]", metaSplit[1].trim()));
        }

        if (metaSplit.length > 1) {

          try {
            result.setQuantity(Integer.valueOf(metaSplit[1].trim()));

          } catch (NumberFormatException e) {
            throw new MalformedRecipeItemException(String.format("[PARSE] Expected integer, got [%s]", metaSplit[1].trim()));
          }
        }
      }
    }

    return result;
  }
}