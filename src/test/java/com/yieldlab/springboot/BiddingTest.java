package com.yieldlab.springboot;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class BiddingTest {

  /**
   * Test Auction. (POST)
   */
  @Test
  public void bidderA() {
    RestTemplate restTemplate = new RestTemplate();
    String json = "{\"id\":\"1\" ,\"attributes\":{\"a\":\"6\"}}";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    String answer = restTemplate.postForObject(Constants.BIDDERS.get("a"), entity, String.class);
  }

  @Test
  public void bidderB() {
    RestTemplate restTemplate = new RestTemplate();
    String json = "{\"id\":\"2\" ,\"attributes\":{\"b\":\"5\"}}";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    String answer = restTemplate.postForObject(Constants.BIDDERS.get("b"), entity, String.class);
  }

  @Test
  public void bidderC() {
    RestTemplate restTemplate = new RestTemplate();
    String json = "{\"id\":\"3\" ,\"attributes\":{\"c\":\"10\"}}";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    String answer = restTemplate.postForObject(Constants.BIDDERS.get("c"), entity, String.class);
  }

}
