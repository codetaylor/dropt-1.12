package com.codetaylor.mc.dropt.modules.dropt.rule.log;

import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.FileWriter;
import java.io.IOException;

public class LoggerWrapper
    implements ILogger {

  private final Logger logger;
  private FileWriter fileWriter;

  public LoggerWrapper(Logger logger, @Nullable FileWriter fileWriter) {

    this.logger = logger;
    this.fileWriter = fileWriter;
  }

  @Override
  public void warn(String message) {

    this.logger.warn(message);
    this.appendFile("[WARN]  " + message);
  }

  @Override
  public void error(String message) {

    this.logger.error(message);
    this.appendFile("[ERROR] " + message);
  }

  @Override
  public void error(String message, Throwable error) {

    this.logger.error(message, error);
    this.appendFile("[ERROR] " + message);
    this.appendFile("[ERROR] " + error.getLocalizedMessage());
  }

  private void appendFile(String message) {

    if (this.fileWriter != null) {

      try {
        this.fileWriter.write(message + System.lineSeparator());
        this.fileWriter.flush();

      } catch (IOException e) {
        this.logger.error("", e);
      }
    }
  }
}
