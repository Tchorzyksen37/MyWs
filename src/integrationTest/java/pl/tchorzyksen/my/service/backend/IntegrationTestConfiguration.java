package pl.tchorzyksen.my.service.backend;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import pl.tchorzyksen.my.service.backend.email.sender.service.services.EmailSender;

@Getter
@TestConfiguration
@EnableConfigurationProperties
public class IntegrationTestConfiguration {

  @Value("${app.security.tokenSecret}")
  private String testTokenSecret;

  @Bean
  EmailSender fakeEmailSenderService() {
    return () -> System.out.println("Send email is not tested automatically.");
  }
}
