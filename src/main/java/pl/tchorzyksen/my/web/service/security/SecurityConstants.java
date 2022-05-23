package pl.tchorzyksen.my.web.service.security;

import pl.tchorzyksen.my.web.service.SpringApplicationContext;

public class SecurityConstants {
  public static final long EXPIRATION_TIME = 1_600_000L;
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/user";

  public static String getTokenSecret() {

    AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
    return appProperties.getTokeSecret();
  }
}
