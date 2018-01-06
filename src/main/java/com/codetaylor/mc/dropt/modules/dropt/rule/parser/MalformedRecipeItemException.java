package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

/**
 * Created by codetaylor on 11/19/2016.
 */
public class MalformedRecipeItemException
    extends Exception {

  public MalformedRecipeItemException() {

  }

  public MalformedRecipeItemException(String message) {

    super(message);
  }

  public MalformedRecipeItemException(String message, Throwable cause) {

    super(message, cause);
  }

  public MalformedRecipeItemException(Throwable cause) {

    super(cause);
  }

  public MalformedRecipeItemException(
      String message,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace
  ) {

    super(message, cause, enableSuppression, writableStackTrace);
  }
}
