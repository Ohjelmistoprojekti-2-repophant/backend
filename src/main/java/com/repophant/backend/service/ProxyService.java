package com.repophant.backend.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Enumeration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ProxyService {

  private static final Logger logger = LogManager.getLogger(ProxyService.class);

  private static final String DOMAIN = "api.github.com";

  @Value("${spring.security.oauth2.client.registration.github.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.github.client-secret}")
  private String clientSecret;

  public ResponseEntity<String> processProxyRequest(
      String body,
      HttpMethod method,
      HttpServletRequest request,
      HttpServletResponse response,
      String traceId)
      throws URISyntaxException {

    ThreadContext.put("traceId", traceId);

    // Build the request URL
    String requestUrl = request.getRequestURI().substring(7);
    URI uri = buildUri(requestUrl, request.getQueryString());

    // Prepare headers
    HttpHeaders headers = prepareHeaders(request, traceId);

    // Create HTTP entity
    HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);

    // Execute the request
    return executeRequest(uri, method, httpEntity);
  }

  private URI buildUri(String requestUrl, String queryString) throws URISyntaxException {
    URI baseUri = new URI("https", null, DOMAIN, -1, null, null, null);
    return UriComponentsBuilder.fromUri(baseUri)
        .path(requestUrl)
        .query(queryString)
        .build(true)
        .toUri();
  }

  private HttpHeaders prepareHeaders(HttpServletRequest request, String traceId) {
    HttpHeaders headers = new HttpHeaders();

    // Copy headers from the original request
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      headers.set(headerName, request.getHeader(headerName));
    }

    // Add custom headers
    headers.set("TRACE", traceId);

    // Add Basic Authorization header
    String credentials = clientId + ":" + clientSecret;
    String encodedAuthorization = Base64.getEncoder().encodeToString(credentials.getBytes());
    headers.set("Authorization", "Basic " + encodedAuthorization);

    // Remove unnecessary headers
    headers.remove(HttpHeaders.ACCEPT_ENCODING);
    headers.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);

    return headers;
  }

  private ResponseEntity<String> executeRequest(
      URI uri, HttpMethod method, HttpEntity<String> httpEntity) {
    RestTemplate restTemplate =
        new RestTemplate(
            new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

    try {
      ResponseEntity<String> serverResponse =
          restTemplate.exchange(uri, method, httpEntity, String.class);

      // Clean up response headers
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.putAll(serverResponse.getHeaders());
      responseHeaders.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
      responseHeaders.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS);

      return ResponseEntity.status(serverResponse.getStatusCode())
          .headers(responseHeaders)
          .body(serverResponse.getBody());
    } catch (HttpStatusCodeException e) {
      logger.error(e.getMessage());
      return ResponseEntity.status(e.getStatusCode())
          .headers(e.getResponseHeaders())
          .body(e.getResponseBodyAsString());
    }
  }

  @Recover
  public ResponseEntity<String> recoverFromRestClientErrors(
      Exception e,
      String body,
      HttpMethod method,
      HttpServletRequest request,
      HttpServletResponse response,
      String traceId) {

    logger.error("Retry method for the following URL failed: " + request.getRequestURI());
    logger.error("Error message: " + e.getMessage(), e);

    throw new RuntimeException(
        "There was an error processing your request. Please try again later.");
  }
}
