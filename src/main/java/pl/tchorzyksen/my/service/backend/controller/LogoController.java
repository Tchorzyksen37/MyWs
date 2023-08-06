package pl.tchorzyksen.my.service.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.tchorzyksen.my.service.backend.model.Logo;
import pl.tchorzyksen.my.service.backend.service.LogoService;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/logo")
class LogoController {

  private final ResourceLoader resourceLoader;

  private final LogoService logoService;

  @GetMapping(produces = MediaType.IMAGE_GIF_VALUE)
  public byte[] getLogo() throws IOException {
    return resourceLoader.getResource("classpath:logo/applause-leonardo-dicaprio.gif")
        .getInputStream()
        .readAllBytes();
  }

  @PostMapping(consumes = {"multipart/form-data"})
  public void uploadLogo(@RequestParam MultipartFile image) throws IOException {
    var logo = new Logo(image.getOriginalFilename(), image.getSize(),
            image.getBytes(), image.getContentType());
    logoService.saveLogo(logo);
  }

}
