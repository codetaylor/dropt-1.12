package com.codetaylor.mc.dropt.modules.dropt.rule.log;

import java.io.FileWriter;
import java.io.IOException;

public class LogFileWrapper {

  private FileWriter fileWriter;

  public LogFileWrapper(FileWriter fileWriter) {

    this.fileWriter = fileWriter;
  }

  public void info(String message) {

    this.write("[INFO]  " + message);
  }

  public void debug(String message) {

    this.write("[DEBUG] " + message);
  }

  private void write(String message) {

    if (this.fileWriter == null) {
      return;
    }

    try {
      this.fileWriter.write(message + System.lineSeparator());
      this.fileWriter.flush();

    } catch (IOException e) {
      //
    }
  }
}
