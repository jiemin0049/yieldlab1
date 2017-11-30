package com.yieldlab.springboot.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Strings;
import com.yieldlab.springboot.Constants;

@RestController
public class BiddingController {

  RestTemplate restTemplate = new RestTemplate();

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<String> getSession(@PathVariable("id") String id, HttpServletRequest request) {
    List<String> answerList = new ArrayList<>();

    Map<String, String[]> parameterMap = request.getParameterMap();
    for (Entry<String, String[]> entry : parameterMap.entrySet()) {
      String bidder = entry.getKey();
      String value = entry.getValue()[0];
      String url = Constants.BIDDERS.get(bidder);
      if (!Strings.isNullOrEmpty(url)) {
        JSONObject json = getRequestJson(id, bidder, value);
        String answer = bidRequest(json, url);
        answerList.add(answer);
      }
    }

//    try {
//      dummyRead(answerList);
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }

    return new ResponseEntity<String>(getWinBidder(answerList), HttpStatus.OK);
  }

  private JSONObject getRequestJson(String id, String bidder, String value) {
    JSONObject request = new JSONObject();
    request.put("id", id);
    JSONObject attr = new JSONObject();
    attr.put(bidder, value);
    request.put("attributes", attr);
    return request;
  }

  private String bidRequest(JSONObject json, String url) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(json.toString(), headers);
    return restTemplate.postForObject(url, entity, String.class);
  }

  private String getWinBidder(List<String> answerList) {
    int higherPrice = 0;
    String winner = "";
    for (String answer : answerList) {
      JSONObject jsonObj = new JSONObject(answer);
      int price = jsonObj.getInt("bid");
      if (higherPrice < price) {
        higherPrice = price;
        String content = jsonObj.getString("content");
        winner = content.replace("$price$", higherPrice + "");
      }
    }
    return winner;
  }

  /**
   * For dummy testing witout docker.
   * 
   * @param answerList
   * @throws FileNotFoundException
   * @throws IOException
   */
  private void dummyRead(List<String> answerList) throws FileNotFoundException, IOException {
    File file = new File(getClass().getResource("temp.txt").getFile());
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        answerList.add(line);
      }
    }
  }

}