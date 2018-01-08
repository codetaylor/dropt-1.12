package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import com.codetaylor.mc.dropt.modules.dropt.rule.ILogger;
import com.codetaylor.mc.dropt.modules.dropt.rule.LogFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.Rule;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;

public interface IRuleListParser {

  void parse(RecipeItemParser parser, RuleList ruleList, Rule rule, ILogger logger, LogFileWrapper logFileWrapper);
}
