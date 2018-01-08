package com.codetaylor.mc.dropt.modules.dropt.rule.data;

import javax.annotation.Nonnull;
import java.util.List;

public class RuleList
    implements Comparable<RuleList> {

  public transient String _filename;

  public boolean debug;
  public int priority;
  public List<Rule> rules;

  @Override
  public int compareTo(@Nonnull RuleList otherRule) {

    return Integer.compare(this.priority, otherRule.priority);
  }
}
