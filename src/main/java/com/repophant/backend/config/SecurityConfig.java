package com.repophant.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
        .authorizeHttpRequests(
            authorizeRequests ->
                authorizeRequests
                    .requestMatchers(new AntPathRequestMatcher("/api/github-token"))
                    .authenticated()
                    .requestMatchers(new AntPathRequestMatcher("/api/user-info"))
                    .authenticated()
                    .anyRequest()
                    .permitAll())
        .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("http://localhost:5173", true));

    return http.build();
  }
}
