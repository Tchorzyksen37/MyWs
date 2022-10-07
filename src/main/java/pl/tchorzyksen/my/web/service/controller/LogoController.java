package pl.tchorzyksen.my.web.service.controller;

import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LogoController {

  private final ResourceLoader resourceLoader;

  @GetMapping(value = "/logo", produces = MediaType.IMAGE_GIF_VALUE)
  public byte[] getLogo() throws IOException {
    return resourceLoader.getResource("classpath:logo/applause-leonardo-dicaprio.gif")
        .getInputStream()
        .readAllBytes();
  }

}

