package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

import org.apache.logging.log4j.Logger;

public class LoggerWrapper
    implements ILogger {

  private final Logger logger;

  public LoggerWrapper(Logger logger) {

    this.logger = logger;
  }

  @Override
  public void warn(String message) {

    this.logger.warn(message);
  }

  @Override
  public void error(String message) {

    this.logger.error(message);
  }

  @Override
  public void error(String message, Throwable error) {

    this.logger.error(message, error);
  }
}
