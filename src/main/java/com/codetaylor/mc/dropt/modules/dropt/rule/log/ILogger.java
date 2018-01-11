package com.codetaylor.mc.dropt.modules.dropt.rule.log;

public interface ILogger {

  void warn(String message);

  void error(String message);

  void error(String message, Throwable error);

}
