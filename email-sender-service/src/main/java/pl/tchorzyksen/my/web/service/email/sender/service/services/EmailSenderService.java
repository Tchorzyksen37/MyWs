package pl.tchorzyksen.my.web.service.email.sender.service.services;

import java.io.IOException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.tchorzyksen.my.web.service.email.sender.service.TemplateProcessor;

@Service
public class EmailSenderService {
  @Autowired
  private JavaMailSender emailSender;

  @Autowired
  private TemplateProcessor templateProcessor;

  public void sendSimpleMessage() throws IOException {

    try {
      MimeBodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent(templateProcessor.processTemplate(), "text/html; charset=utf-8");

      MimeMessage message = emailSender.createMimeMessage();
      message.addRecipients(Message.RecipientType.TO, "evife2@gmail.com");
      message.setSubject("TEST - send email using Java");
      message.setText("This is the test.");
      Multipart multipart = new MimeMultipart();

      multipart.addBodyPart(messageBodyPart);
      message.setContent(multipart);


      emailSender.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }

  }
}
