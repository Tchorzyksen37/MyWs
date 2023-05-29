package pl.tchorzyksen.my.web.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.tchorzyksen.my.web.service.email.sender.service.services.EmailSender;
import pl.tchorzyksen.my.web.service.orm.GreetingEntity;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequiredArgsConstructor
public class GreetingController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  private final EmailSender emailSenderService;

  @GetMapping("/greeting")
  public ResponseEntity<GreetingEntity> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    emailSenderService.sendSimpleMessage();
    return ResponseEntity.ok(new GreetingEntity(counter.incrementAndGet(), String.format(template, name)));
  }
}
