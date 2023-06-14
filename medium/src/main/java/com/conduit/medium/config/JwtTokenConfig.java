package com.conduit.medium.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * class to read config properties of a jwt token from properties file.
 */
@Slf4j
@NoArgsConstructor
@Data
@Configuration
@ConfigurationProperties(prefix = "medium.jwt")
public class JwtTokenConfig {
  private String tokenSecret;
  private String expirationMs;
}
