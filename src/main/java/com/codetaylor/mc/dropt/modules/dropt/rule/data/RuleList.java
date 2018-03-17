package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RuleList
    implements Comparable<RuleList> {

  public transient String _filename;

  public int priority;
  public List<Rule> rules = new ArrayList<>();

  @Override
  public int compareTo(@Nonnull RuleList otherRule) {

    return Integer.compare(otherRule.priority, this.priority);
  }
}
