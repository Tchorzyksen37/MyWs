package pl.tchorzyksen.my.web.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.tchorzyksen.my.web.service.model.dto.UserDto;
import pl.tchorzyksen.my.web.service.model.request.UserLoginRequest;
import pl.tchorzyksen.my.web.service.service.UserService;

import java.io.IOException;
import java.util.Date;

import static pl.tchorzyksen.my.web.service.security.SecurityConstants.EXPIRATION_TIME;
import static pl.tchorzyksen.my.web.service.security.SecurityConstants.HEADER_STRING;
import static pl.tchorzyksen.my.web.service.security.SecurityConstants.HEADER_USER_ID;
import static pl.tchorzyksen.my.web.service.security.SecurityConstants.TOKEN_PREFIX;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final UserService userService;

  private final SecurityConfiguration securityConfiguration;

  private final ObjectMapper objectMapper;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      var userLoginRequest = objectMapper.readValue(request.getInputStream(), UserLoginRequest.class);
      var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userLoginRequest.email(),
              userLoginRequest.password());
      return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);

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
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, securityConfiguration.getTokenSecret())
                    .compact();

    UserDto userDto = userService.getUserByEmail(userName);

    response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    response.addHeader(HEADER_USER_ID, userDto.getUserId());
  }

}
