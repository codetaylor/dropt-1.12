package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import org.apache.logging.log4j.Logger;

public interface IRuleListParser {

  void parse(RecipeItemParser parser, RuleList ruleList, Rule rule, Logger logger);
}
