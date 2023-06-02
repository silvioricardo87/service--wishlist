package com.silvioricardo.wishlist.infrastructure.config.filter;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
  @Value("${api-key.name}")
  private String apiKeyName;

  @Value("${api-key.value}")
  private String apiKeyValue;

  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  protected static final String[] listaPermtida = {
      "/login",
      "/actuator",
      "/actuator/*/**",
      "/api-docs/**",
      "/v3/api-docs/**",
      "/swagger-resources/**",
      "/swagger-ui/**",
      "/webjars/**"
  };

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    boolean isPermitted = Arrays.stream(listaPermtida).anyMatch(p -> pathMatcher.match(p, request.getServletPath()));

    if(isPermitted) {
      filterChain.doFilter(request, response);
      return;
    }

    if(request.getHeader(apiKeyName) == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    if(request.getHeader(apiKeyName).equals(apiKeyValue)){
      filterChain.doFilter(request, response);
    } else {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}
