package com.codetaylor.mc.dropt.modules.dropt.rule.log;

import com.codetaylor.mc.dropt.modules.dropt.Util;

import java.io.FileWriter;
import java.io.IOException;

public class DebugFileWrapper {

  private FileWriter fileWriter;

  public DebugFileWrapper(FileWriter fileWriter) {

    this.fileWriter = fileWriter;
  }

  public void info(String message) {

    this.write("[INFO]  " + message);
  }

  public void debug(String message) {

    this.write("[DEBUG] " + message);
  }

  public void close() {

    if (this.fileWriter != null) {
      Util.closeSilently(this.fileWriter);
    }
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
