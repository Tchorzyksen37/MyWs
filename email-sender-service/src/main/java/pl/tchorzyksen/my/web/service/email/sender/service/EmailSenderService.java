package pl.tchorzyksen.my.web.service.email.sender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
  @Autowired
  private JavaMailSender emailSender;

  public void sendSimpleMessage() {

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo("evife2@gmail.com");
    message.setSubject("TEST - send email using Java");
    message.setText("This is the test.");
    emailSender.send(message);
  }
}
