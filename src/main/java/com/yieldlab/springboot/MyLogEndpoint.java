package com.yieldlab.springboot;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;

/**
 * Dynamically change log level.
 * @author Zhang
 *
 */
public class MyLogEndpoint extends AbstractEndpoint<Boolean> {

  AtomicBoolean b = new AtomicBoolean();

  public MyLogEndpoint() {
    super("mgrLogging", false, true);
  }

  @Override
  public Boolean invoke() {
    return b.getAndSet(!b.get());
  }

}
