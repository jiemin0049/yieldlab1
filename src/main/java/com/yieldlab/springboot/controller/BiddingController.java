package com.yieldlab.springboot.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Strings;
import com.yieldlab.springboot.Constants;
import com.yieldlab.springboot.model.Bidder;

@RestController
public class BiddingController {

  RestTemplate restTemplate = new RestTemplate();

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<String> getSession(@PathVariable("id") String id, HttpServletRequest request) {
    List<Bidder> bidderList = new ArrayList<>();

    Map<String, String[]> parameterMap = request.getParameterMap();
    JSONObject attrs = getAttributesJson(parameterMap);
    JSONObject requestJson = new JSONObject();
    requestJson.put("id", id);
    requestJson.put("attributes", attrs);

    for (Entry<String, String> entry : Constants.BIDDERS.entrySet()) {
      String url = entry.getValue();
      String answer = bidRequest(requestJson, url);
      if (!Strings.isNullOrEmpty(answer)) {
        Bidder bidder = convertToBidder(answer);
        bidderList.add(bidder);
      }
    }

    return new ResponseEntity<String>(getWinBidder(bidderList), HttpStatus.OK);
  }

  private JSONObject getAttributesJson(Map<String, String[]> parameterMap) {
    JSONObject attributes = new JSONObject();
    for (Entry<String, String[]> entry : parameterMap.entrySet()) {
      String bidderName = entry.getKey();
      String value = entry.getValue()[0];
      attributes.put(bidderName, value);
    }
    return attributes;
  }

  private String bidRequest(JSONObject json, String url) {
    String answer = "";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(json.toString(), headers);
    try {
      answer = restTemplate.postForObject(url, entity, String.class);
    } catch (RestClientException e) {
      // TODO: here should handle exception and write in log.
      e.printStackTrace();
    }
    return answer;
  }

  private Bidder convertToBidder(String bidResponse) {
    JSONObject jsonObj = new JSONObject(bidResponse);
    int bid = jsonObj.getInt("bid");
    String content = jsonObj.getString("content");
    return new Bidder(content, bid);

  }

  private String getWinBidder(List<Bidder> bidderList) {
    if (bidderList.size() == 0) {
      return "Error: no bidder found";
    }

    Collections.sort(bidderList, new Comparator<Bidder>() {
      @Override
      public int compare(Bidder b1, Bidder b2) {
        if (b1.getBid() != b2.getBid()) {
          return b1.getBid() - b2.getBid();
        } else {
          return -(b1.getName().compareTo(b2.getName()));
        }

      }

    });
    // Get last element from list.
    Bidder bidder = bidderList.get(bidderList.size() - 1);
    return bidder.getName() + ":" + bidder.getBid();

  }
}