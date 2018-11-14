package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.api.reference.EnumListType;
import com.codetaylor.mc.dropt.modules.dropt.rule.match.ItemMatchEntry;

import java.util.ArrayList;
import java.util.List;

public class RuleMatchDrops {

  public transient List<ItemMatchEntry> _drops = new ArrayList<>();

  public EnumListType type = EnumListType.WHITELIST;
  public String[] drops = new String[0];

}
