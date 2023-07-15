package com.conduit.medium.security.jwt;

import com.conduit.medium.security.service.JwtUserDetailsService;
import com.conduit.medium.util.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * class to intercept requests for auth token.
 */
@Slf4j
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
                                  final HttpServletResponse response,
                                  final FilterChain filterChain)
      throws ServletException, IOException {
    try {
      final String jwtToken = parseJwt(request);

      if (Objects.nonNull(jwtToken) && jwtUtil.validateJwtToken(jwtToken)) {
        log.debug("Authenticated jwt token");
        final String userName = jwtUtil.getUsernameFromToken(jwtToken);
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(userName);
        final UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    } catch (final Exception exception) {
      log.error("Exception while authentication-> [{}]", exception.getMessage());
    }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(final HttpServletRequest request) {
    final String authHeader = request.getHeader(Constant.Auth.AUTHORIZATION_HEADER);
    if (StringUtils.hasText(authHeader) &&
        authHeader.startsWith(Constant.Auth.AUTHORIZATION_HEADER_VALUE_PREFIX)) {
      return authHeader.substring(7);
    }
    return null;
  }
}
