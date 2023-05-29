package pl.tchorzyksen.my.web.service.email.sender.service.configuration;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@Profile("local")
public class EmailSenderConfiguration {

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);

    mailSender.setUsername("tchorzyksen@gmail.com");
    Optional<String> password = Optional.ofNullable(System.getenv("SMTP_PASSWORD"));
    mailSender.setPassword(password.orElseThrow(() -> new NoSuchElementException("Password for SMTP does not exist.")));

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");

    return mailSender;
  }

}
