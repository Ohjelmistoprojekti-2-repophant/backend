package com.repophant.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final OAuth2AuthorizedClientService authorizedClientService;

  public UserController(OAuth2AuthorizedClientService authorizedClientService) {
    this.authorizedClientService = authorizedClientService;
  }

  @GetMapping("api/user-info")
  public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
    return principal.getAttributes();
  }

  @GetMapping("api/github-token")
  public ResponseEntity<Map<String, String>> getGithubToken(HttpServletRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication instanceof OAuth2AuthenticationToken) {
      OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
      OAuth2AuthorizedClient client =
          authorizedClientService.loadAuthorizedClient(
              oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

      if (client != null) {
        String token = client.getAccessToken().getTokenValue();
        request.setAttribute("oauthToken", token);
        return ResponseEntity.ok(Map.of("token", token));
      }
    }

    return ResponseEntity.status(401).body(Map.of("error", "Not authenticated with GitHub"));
  }
}
