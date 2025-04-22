package com.repophant.backend.controller;

import com.repophant.backend.service.ProxyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyController {
  @Autowired ProxyService service;

  @RequestMapping("/api/gh/**")
  public ResponseEntity<String> sendRequestToSPM(
      @RequestBody(required = false) String body,
      HttpMethod method,
      HttpServletRequest request,
      HttpServletResponse response)
      throws URISyntaxException {
    return service.processProxyRequest(
        body, method, request, response, UUID.randomUUID().toString());
  }
}
