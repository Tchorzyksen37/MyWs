package pl.tchorzyksen.my.web.service.email.sender.service.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailProperties {

  private String from;

  private String senderInfo;

}

