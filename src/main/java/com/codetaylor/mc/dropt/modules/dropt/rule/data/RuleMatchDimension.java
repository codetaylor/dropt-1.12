package com.codetaylor.mc.dropt.modules.dropt.rule.data;

public class RuleMatchDimension {

  public EnumListType type = EnumListType.WHITELIST;
  public int[] ids = new int[0];

  public boolean matches(int dimension) {

    return this.ids.length == 0
        || this.type == EnumListType.WHITELIST
        && this.contains(this.ids, dimension)
        || this.type == EnumListType.BLACKLIST
        && !this.contains(this.ids, dimension);
  }

  private boolean contains(int[] ids, int toMatch) {

    for (int id : ids) {

      if (id == toMatch) {
        return true;
      }
    }

    return false;
  }
}
