package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

public interface ILogger {

  void warn(String message);

  void error(String message);

  void error(String message, Throwable error);

}
