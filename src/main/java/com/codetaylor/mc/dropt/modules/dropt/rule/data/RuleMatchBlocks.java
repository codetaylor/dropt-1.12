package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import com.codetaylor.mc.dropt.modules.dropt.rule.match.BlockMatchEntry;

import java.util.ArrayList;
import java.util.List;

public class RuleMatchBlocks {

  public transient List<BlockMatchEntry> _blocks = new ArrayList<>();

  public EnumListType type = EnumListType.WHITELIST;
  public String[] blocks = new String[0];

}
