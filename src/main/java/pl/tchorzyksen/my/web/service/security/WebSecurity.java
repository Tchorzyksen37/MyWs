package pl.tchorzyksen.my.web.service.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.tchorzyksen.my.web.service.service.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private static final String[] ACCESSIBLE_ENDPOINTS = {"/greeting/**"};

  private final UserService userService;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userService = userService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests(
            authorizeRequests ->
                authorizeRequests
                    .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
                    .permitAll()
                    .antMatchers(ACCESSIBLE_ENDPOINTS)
                    .permitAll()
                    .anyRequest()
                    .fullyAuthenticated())
        .csrf()
        .disable()
        .addFilter(getAuthenticationFilter())
        .addFilter(new AuthorizationFilter(authenticationManager()))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  public AuthenticationFilter getAuthenticationFilter() throws Exception {

    final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
    filter.setFilterProcessesUrl("/user/login");
    return filter;
  }
}
