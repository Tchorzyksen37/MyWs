package pl.tchorzyksen.my.web.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.tchorzyksen.my.web.service.SpringApplicationContext;
import pl.tchorzyksen.my.web.service.model.dto.UserDto;
import pl.tchorzyksen.my.web.service.model.request.UserLoginRequest;
import pl.tchorzyksen.my.web.service.service.UserService;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  private final String tokenSecret;

  public AuthenticationFilter(AuthenticationManager authenticationManager, String tokenSecret) {
    this.authenticationManager = authenticationManager;
    this.tokenSecret = tokenSecret;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    try {
      UserLoginRequest creds =
          new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              creds.email(), creds.password(), new ArrayList<>()));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult) {

    String userName = ((User) authResult.getPrincipal()).getUsername();

    String token =
        Jwts.builder()
            .setSubject(userName)
            .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, tokenSecret)
            .compact();

    UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
    UserDto userDto = userService.getUserByEmail(userName);

    response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

    response.addHeader("UserID", userDto.getUserId());
  }
}
