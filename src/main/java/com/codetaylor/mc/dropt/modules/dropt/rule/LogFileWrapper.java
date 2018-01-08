package com.codetaylor.mc.dropt.modules.dropt.rule;

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

    try {
      this.fileWriter.write(message + System.lineSeparator());

    } catch (IOException e) {
      //
    }
  }
}
