package pl.tchorzyksen.my.web.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

  private final String tokenSecret;

  public AuthorizationFilter(AuthenticationManager authenticationManager, String tokenSecret) {
    super(authenticationManager);
    this.tokenSecret = tokenSecret;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    String header = request.getHeader(SecurityConstants.HEADER_STRING);

    if (header != null && header.startsWith(SecurityConstants.TOKEN_PREFIX)) {

      UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

    String token = request.getHeader(SecurityConstants.HEADER_STRING);

    if (token != null) {
      log.debug("JWT token: {}", token);
      token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

      Optional<String> user = getJwtClaims(token).map(Claims::getSubject);

      if (user.isPresent()) {
        log.debug("User: {}", user);
        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
      }

    }
    log.debug("Token not found in request headers");
    return null;
  }

  private Optional<Claims> getJwtClaims(String token) {
    try {
      return Optional.of(Jwts.parser()
          .setSigningKey(tokenSecret)
          .parseClaimsJws(token)
          .getBody());
    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
             IllegalArgumentException e) {
      log.debug("Authorization failed with message {}", e.getMessage());
    }

    return Optional.empty();
  }
}
