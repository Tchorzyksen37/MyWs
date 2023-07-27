package pl.tchorzyksen.my.web.service.email.sender.service.services;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.tchorzyksen.my.web.service.email.sender.service.TemplateProcessor;
import pl.tchorzyksen.my.web.service.email.sender.service.configuration.EmailProperties;

import java.io.IOException;

@Service
@Profile("local")
@AllArgsConstructor
public class EmailSenderService implements EmailSender {

  private final JavaMailSender emailSender;

  private final TemplateProcessor templateProcessor;

  private final EmailProperties emailProperties;

  public void sendSimpleMessage() {

    try {
      MimeBodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent(templateProcessor.processTemplate(), "text/html; charset=utf-8");

      MimeMessage message = emailSender.createMimeMessage();
      message.setFrom(new InternetAddress(emailProperties.getFrom(), emailProperties.getSenderInfo()));

      message.addRecipients(Message.RecipientType.TO, "evife2@gmail.com");

      message.setSubject("TEST - send email using Java");
      message.setText("This is the test.");

      Multipart multipart = new MimeMultipart();

      multipart.addBodyPart(messageBodyPart);
      message.setContent(multipart);


      emailSender.send(message);
    } catch (MessagingException | IOException e) {
      throw new RuntimeException(e);
    }

  }
}
