package pl.tchorzyksen.my.service.backend.security;

public class SecurityConstants {

  public static final long EXPIRATION_TIME = 1_600_000L;

  public static final String TOKEN_PREFIX = "Bearer ";

  public static final String HEADER_STRING = "Authorization";

  public static final String HEADER_USER_ID = "UserId";

  public static final String SIGN_UP_URL = "/user";

  public static final String LOGIN_URL = "/user/login";

}
