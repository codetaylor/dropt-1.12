package com.codetaylor.mc.dropt.modules.dropt.rule;

public interface ILogger {

  void warn(String message);

  void error(String message);

  void error(String message, Throwable error);

}
