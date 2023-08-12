package pl.tchorzyksen.my.service.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static pl.tchorzyksen.my.service.backend.security.SecurityConstants.HEADER_STRING;
import static pl.tchorzyksen.my.service.backend.security.SecurityConstants.TOKEN_PREFIX;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

  private final SecurityConfiguration securityConfiguration;

  public AuthorizationFilter(AuthenticationManager authenticationManager, SecurityConfiguration securityConfiguration) {
    super(authenticationManager);
    this.securityConfiguration = securityConfiguration;
  }

  @Override
  protected void doFilterInternal(
          HttpServletRequest request, HttpServletResponse response, FilterChain chain)
          throws IOException, ServletException {

    String header = request.getHeader(HEADER_STRING);

    if (header != null && header.startsWith(TOKEN_PREFIX)) {

      UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

    String token = request.getHeader(HEADER_STRING);

    if (token != null) {
      log.debug("JWT token: {}", token);
      token = token.replace(TOKEN_PREFIX, "");

      Optional<String> user = getJwtClaims(token).map(Claims::getSubject);

      if (user.isPresent()) {
        log.debug("User: {}", user);
        return new UsernamePasswordAuthenticationToken(user.get(), null, new ArrayList<>());
      }

    }
    log.debug("Token not found in request headers");
    return null;
  }

  private Optional<Claims> getJwtClaims(String token) {
    try {
      return Optional.of(Jwts.parser()
              .setSigningKey(securityConfiguration.getTokenSecret())
              .parseClaimsJws(token)
              .getBody());
    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
             IllegalArgumentException e) {
      log.debug("Authorization failed with message {}", e.getMessage());
    }

    return Optional.empty();
  }
}
