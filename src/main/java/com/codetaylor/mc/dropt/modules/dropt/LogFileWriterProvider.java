package com.codetaylor.mc.dropt.modules.dropt;

import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LogFileWriterProvider {

  private Path logPath;
  private Logger logger;

  public LogFileWriterProvider(Path logPath, Logger logger) {

    this.logPath = logPath;
    this.logger = logger;
  }

  public FileWriter createLogFileWriter() {

    try {

      if (!Files.exists(this.logPath)) {
        Files.createFile(this.logPath);
      }

      return new FileWriter(this.logPath.toFile(), true);

    } catch (IOException e) {
      this.logger.error("", e);
    }

    return null;
  }
}
