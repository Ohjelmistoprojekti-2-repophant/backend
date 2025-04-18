package com.repophant.backend.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

public class UserControllerTest {

  @Mock private OAuth2AuthorizedClientService authorizedClientService;

  @InjectMocks private UserController userController;

  @Mock private SecurityContext securityContext;

  @Mock private Authentication authentication;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  public void testGetGithubToken_Authenticated() {
    // Setup
    OAuth2AuthenticationToken oauthToken = mock(OAuth2AuthenticationToken.class);
    OAuth2AuthorizedClient client = mock(OAuth2AuthorizedClient.class);
    OAuth2AccessToken accessToken = mock(OAuth2AccessToken.class);

    when(securityContext.getAuthentication()).thenReturn(oauthToken);
    when(oauthToken.getAuthorizedClientRegistrationId()).thenReturn("github");
    when(oauthToken.getName()).thenReturn("test-user");
    when(authorizedClientService.loadAuthorizedClient("github", "test-user")).thenReturn(client);
    when(client.getAccessToken()).thenReturn(accessToken);
    when(accessToken.getTokenValue()).thenReturn("test-token");

    // Test
    ResponseEntity<Map<String, String>> response = userController.getGithubToken();

    // Verify
    assertEquals(200, response.getStatusCode().value());
    assertEquals("test-token", response.getBody().get("token"));
  }

  @Test
  public void testGetGithubToken_NotAuthenticated() {
    // Setup
    when(securityContext.getAuthentication()).thenReturn(null);

    // Test
    ResponseEntity<Map<String, String>> response = userController.getGithubToken();

    // Verify
    assertEquals(401, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals("Not authenticated with GitHub", response.getBody().get("error"));
  }

  @Test
  public void testGetGithubToken_NotOAuthToken() {
    // Setup
    when(securityContext.getAuthentication()).thenReturn(authentication);

    // Test
    ResponseEntity<Map<String, String>> response = userController.getGithubToken();

    // Verify
    assertEquals(401, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals("Not authenticated with GitHub", response.getBody().get("error"));
  }

  @Test
  public void testGetGithubToken_NoClient() {
    // Setup
    OAuth2AuthenticationToken oauthToken = mock(OAuth2AuthenticationToken.class);
    when(securityContext.getAuthentication()).thenReturn(oauthToken);
    when(oauthToken.getAuthorizedClientRegistrationId()).thenReturn("github");
    when(oauthToken.getName()).thenReturn("test-user");
    when(authorizedClientService.loadAuthorizedClient("github", "test-user")).thenReturn(null);

    // Test
    ResponseEntity<Map<String, String>> response = userController.getGithubToken();

    // Verify
    assertEquals(401, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals("Not authenticated with GitHub", response.getBody().get("error"));
  }
}
