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

  /**
   * Get a session with given session id.
   * 
   * @param id
   *          session id.
   * @return
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<String> getSession(@PathVariable("id") String id, HttpServletRequest request) {
    List<String> answerList = new ArrayList<>();

    Map<String, String[]> parameterMap = request.getParameterMap();
    for (Entry<String, String[]> entry : parameterMap.entrySet()) {
      String bidder = entry.getKey();
      String value = entry.getValue()[0];
      String url = Constants.BIDDERS.get(bidder);
      if (!Strings.isNullOrEmpty(url)) {
        String json = getRequestJson(id, bidder, value);
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

  private String getRequestJson(String id, String bidder, String value) {
    return "{\"id\":\"" + id + "\" ,\"attributes\":{\"" + bidder + "\":\"" + value + "\"}}";
  }

  private String bidRequest(String json, String url) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(json, headers);
    return restTemplate.postForObject(url, entity, String.class);
  }

  private String getWinBidder(List<String> answerList) {
    int i = 0;
    String winner = "";
    for (String answer : answerList) {
      JSONObject jsonObj = new JSONObject(answer);
      int value = jsonObj.getInt("bid");
      if (value > i) {
        i = value;
        String content = jsonObj.getString("content");
        winner = content.replace("$price$", value + "");
      }
    }
    return winner;
  }

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