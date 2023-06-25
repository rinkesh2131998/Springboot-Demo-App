package com.conduit.medium.security.jwt;

import com.conduit.medium.config.JwtTokenConfig;
import com.conduit.medium.security.service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * used to create, extract and manage jwt token for auth.
 */
@Slf4j
@Data
@Component
public class JwtUtil {
  private final JwtTokenConfig jwtTokenConfig;

  public String generateJwtToken(final Authentication authentication) {
    final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    final LocalDateTime now = LocalDateTime.now();
    final LocalDateTime expirationTime =
        now.plusSeconds(Long.parseLong(jwtTokenConfig.getExpirationSecond()));

    final Date issuedAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    final Date expiration = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());

    return Jwts.builder()
        .setSubject(userDetails.getEmail())
        .setIssuedAt(issuedAt)
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS512, jwtTokenConfig.getTokenSecret())
        .compact();
  }

  public String getUsernameFromToken(final String jwtToken) {
    return Jwts.parser().setSigningKey(jwtTokenConfig.getTokenSecret()).parseClaimsJws(jwtToken)
        .getBody().getSubject();
  }

  @SuppressWarnings("PlaceholderCountMatchesArgumentCount")
  public boolean validateJwtToken(final String jwtToken) {
    try {
      Jwts.parser().setSigningKey(jwtTokenConfig.getTokenSecret()).parseClaimsJws(jwtToken);
      return true;
    } catch (final SignatureException var1) {
      log.error("Invalid JWT signature: [{}]", var1);
    } catch (final MalformedJwtException var2) {
      log.error("Invalid JWT token: {}", var2);
    } catch (final ExpiredJwtException var3) {
      log.error("JWT token is expired: {}", var3);
    } catch (final UnsupportedJwtException var4) {
      log.error("JWT token is unsupported: {}", var4);
    } catch (final IllegalArgumentException var5) {
      log.error("JWT claims string is empty: {}", var5);
    }
    return false;
  }
}
