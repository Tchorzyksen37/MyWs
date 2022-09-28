package pl.tchorzyksen.my.web.service.controller;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.tchorzyksen.my.web.service.email.sender.service.services.EmailSenderService;
import pl.tchorzyksen.my.web.service.entities.GreetingEntity;

@RestController
public class GreetingController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @Autowired
  private EmailSenderService emailSenderService;

  @GetMapping("/greeting")
  public ResponseEntity<GreetingEntity> greeting(@RequestParam(value = "name", defaultValue = "World") String name) throws IOException {
    emailSenderService.sendSimpleMessage();
    return ResponseEntity.ok(new GreetingEntity(counter.incrementAndGet(), String.format(template, name)));
  }
}
