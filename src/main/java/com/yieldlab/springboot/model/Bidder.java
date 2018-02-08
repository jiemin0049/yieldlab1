package com.yieldlab.springboot.model;

import com.google.common.base.Strings;

public class Bidder {
  private String content;
  private String name;
  private int bid;

  public Bidder(String content, int bid) {
    this.bid = bid;
    this.content = content;
    this.name = retriveName(content);
  }

  public String getContent() {
    return content;
  }

  public String getName() {
    return name;
  }

  public int getBid() {
    return bid;
  }

  private String retriveName(String content) {
    if (Strings.isNullOrEmpty(content)) {
      return "";
    }
    String[] parts = content.split(":");
    return parts[0].trim();
  }
}
