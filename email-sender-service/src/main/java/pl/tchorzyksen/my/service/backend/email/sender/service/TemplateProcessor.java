package pl.tchorzyksen.my.service.backend.email.sender.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class TemplateProcessor {

  private final Configuration freemarkerTemplateConfiguration;

  private static final String EMAIL_TEMPLATE_NAME = "hello.ftl";

  private static final Map<String, Object> DATA_MODEL = Map.of("name", "This is the test ( ͡° ͜ʖ ͡°)");

  public String processTemplate() throws IOException {

    Template template = getTemplate(EMAIL_TEMPLATE_NAME);

    try (Writer outputStream = new StringWriter()) {
      template.process(DATA_MODEL, outputStream);
      return outputStream.toString();
    } catch (TemplateException e) {
      log.error("Could not process template");
      throw new RuntimeException(e);
    }

  }

  private Template getTemplate(String templatePath) throws IOException {
    log.debug("Get template");
    return freemarkerTemplateConfiguration.getTemplate(templatePath);
  }
}
