package com.yieldlab.springboot;

import java.util.HashMap;
import java.util.Map;

public final class Constants {

  public static final Map<String, String> BIDDERS = new HashMap<>();
  static {
    BIDDERS.put("a", "http://localhost:8081");
    BIDDERS.put("b", "http://localhost:8082");
    BIDDERS.put("c", "http://localhost:8083");
  }
}
