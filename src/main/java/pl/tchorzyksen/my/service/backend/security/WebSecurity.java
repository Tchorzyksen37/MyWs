package pl.tchorzyksen.my.service.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import pl.tchorzyksen.my.service.backend.service.UserService;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static pl.tchorzyksen.my.service.backend.security.SecurityConstants.LOGIN_URL;
import static pl.tchorzyksen.my.service.backend.security.SecurityConstants.SIGN_UP_URL;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

  private static final String[] ACCESSIBLE_ENDPOINTS = {"/greeting/**", "/actuator/**", "/error/**", "/favicon.ico"};

  private final UserService userService;

  private final SecurityConfiguration securityConfiguration;

  private final ObjectMapper objectMapper;

  @Bean
  protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

    httpSecurity.authorizeHttpRequests(authorizeHttpRequestsConfigurer -> authorizeHttpRequestsConfigurer
                    .requestMatchers(ACCESSIBLE_ENDPOINTS).permitAll()
                    .requestMatchers(POST, SIGN_UP_URL).permitAll()
                    .anyRequest().fullyAuthenticated())
            .csrf(AbstractHttpConfigurer::disable)
            .addFilter(authenticationFilter(authenticationManager))
            .addFilter(authorizationFilter(authenticationManager))
            .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                    .sessionCreationPolicy(STATELESS));

    return httpSecurity.build();
  }

  private AuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) {
    var authFilter = new AuthenticationFilter(userService, securityConfiguration, objectMapper);
    authFilter.setFilterProcessesUrl(LOGIN_URL);
    authFilter.setAuthenticationManager(authenticationManager);
    return authFilter;
  }

  private AuthorizationFilter authorizationFilter(AuthenticationManager authenticationManager) {
    return new AuthorizationFilter(authenticationManager, securityConfiguration);
  }

}
