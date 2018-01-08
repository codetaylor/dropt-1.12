package com.codetaylor.mc.dropt.modules.dropt;

import java.io.Closeable;
import java.io.IOException;

public class Util {

  public static void closeSilently(Closeable closeable) {

    if (closeable == null) {
      return;
    }

    try {
      closeable.close();

    } catch (IOException e) {
      //
    }

  }

}
