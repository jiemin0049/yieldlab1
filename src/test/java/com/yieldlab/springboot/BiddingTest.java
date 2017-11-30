package com.yieldlab.springboot;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class BiddingTest {

  /**
   * Test Auction. (POST)
   */
  
  /*
  @Test
  public void bidderA() {
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("id", "1");
    JSONObject attr = new JSONObject();
    attr.put("a", "6");
    jsonObj.put("attributes", attr);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    
    HttpEntity<String> entity = new HttpEntity<String>(jsonObj.toString(), headers);
    
    RestTemplate restTemplate = new RestTemplate();
    String answer = restTemplate.postForObject(Constants.BIDDERS.get("a"), entity, String.class);
  }

  @Test
  public void bidderB() {
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("id", "2");
    JSONObject attr = new JSONObject();
    attr.put("b", "5");
    jsonObj.put("attributes", attr);
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    
    HttpEntity<String> entity = new HttpEntity<String>(jsonObj.toString(), headers);

    RestTemplate restTemplate = new RestTemplate();
    String answer = restTemplate.postForObject(Constants.BIDDERS.get("b"), entity, String.class);
  }

  @Test
  public void bidderC() {
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("id", "3");
    JSONObject attr = new JSONObject();
    attr.put("c", "10");
    jsonObj.put("attributes", attr);
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> entity = new HttpEntity<String>(jsonObj.toString(), headers);
    
    RestTemplate restTemplate = new RestTemplate();
    String answer = restTemplate.postForObject(Constants.BIDDERS.get("c"), entity, String.class);
  }
  */
}
