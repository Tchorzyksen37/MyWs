package pl.tchorzyksen.my.service.backend.email.sender.service.configuration;

import freemarker.template.Configuration;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
@org.springframework.context.annotation.Configuration
public class TemplateConfiguration {

  private static final String TEMPLATE_REPOSITORY_PATH = "email-sender-service/src/main/resources/templates/email";

  @Bean
  public Configuration freemarkerTemplateConfiguration() throws IOException {
    Configuration templateConfiguration = new Configuration(Configuration.VERSION_2_3_21);

    templateConfiguration.setDirectoryForTemplateLoading(new File(TEMPLATE_REPOSITORY_PATH));
    templateConfiguration.setDefaultEncoding("UTF-8");
    return templateConfiguration;
  }
}
