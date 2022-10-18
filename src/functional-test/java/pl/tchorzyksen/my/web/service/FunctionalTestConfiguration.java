package pl.tchorzyksen.my.web.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import pl.tchorzyksen.my.web.service.email.sender.service.services.EmailSenderService;

@Slf4j
@TestConfiguration
@EnableConfigurationProperties
public class FunctionalTestConfiguration {

  @Getter
  @Value("${app.security.tokenSecret}")
  private String testTokenSecret;

  @Bean
  EmailSenderService fakeEmailSenderService() {
    return () -> log.debug("Send email is not tested automatically.");
  }
}
