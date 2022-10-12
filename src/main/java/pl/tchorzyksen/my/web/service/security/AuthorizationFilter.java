package pl.tchorzyksen.my.web.service.security;

import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.ArrayList;
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
      token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

      log.debug("JWT token: {}", token);
      String user =
          Jwts.parser()
              .setSigningKey(tokenSecret)
              .parseClaimsJws(token)
              .getBody()
              .getSubject();

      log.debug("User: {}", user);
      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
      }

      return null;
    }

    log.debug("JWToken not found - cannot authorize");
    return null;
  }
}
