package pl.tchorzyksen.my.service.backend.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.Instant;

import static java.time.ZoneOffset.UTC;

@TestConfiguration
public class TestClockConfiguration {

  public static final Instant TEST_TIME = Instant.parse("2023-08-04T14:40:00Z");

  @Bean
  Clock testClock() {
    return Clock.fixed(TEST_TIME, UTC);
  }

}
