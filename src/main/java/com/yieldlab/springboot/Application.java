package com.yieldlab.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = { "com.yieldlab.springboot" })
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  @ConditionalOnMissingBean(MyLogEndpoint.class)
  public MyLogEndpoint changeLogEndpoint() {
    return new MyLogEndpoint();
  }
}
