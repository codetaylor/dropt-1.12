package com.codetaylor.mc.dropt.modules.dropt.rule.parser;

/**
 * Created by codetaylor on 11/17/2016.
 */
public class ParseResult {

  public static final ParseResult NULL = new ParseResult();

  private String domain;
  private String path;
  private int meta;
  private int quantity;

  public void setDomain(String domain) {

    this.domain = domain;
  }

  public void setPath(String path) {

    this.path = path;
  }

  public void setMeta(int meta) {

    this.meta = meta;
  }

  public void setQuantity(int quantity) {

    this.quantity = quantity;
  }

  public String getDomain() {

    return domain;
  }

  public String getPath() {

    return path;
  }

  public int getMeta() {

    return meta;
  }

  public int getQuantity() {

    return quantity;
  }

  @Override
  public String toString() {

    if (this == ParseResult.NULL) {
      return "null";
    }

    return this.domain + ":" + this.path + ":" + this.meta + ((this.quantity != 1) ? " * " + this.quantity : "");
  }
}
