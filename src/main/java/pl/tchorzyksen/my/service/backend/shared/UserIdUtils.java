package pl.tchorzyksen.my.service.backend.shared;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.Random;

@UtilityClass
public class UserIdUtils {

  private final Random RANDOM = new SecureRandom();

  private static final String ALPHABET =
      "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  public static String generateUserId(int length) {
    return generateRandomString(length);
  }

  private String generateRandomString(int length) {
    StringBuilder returnedValue = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      returnedValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
    }

    return new String(returnedValue);
  }
}
