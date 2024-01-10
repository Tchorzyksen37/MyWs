package pl.tchorzyksen.my.service.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.tchorzyksen.my.service.backend.model.ListObjectsResult;
import pl.tchorzyksen.my.service.backend.model.Logo;
import pl.tchorzyksen.my.service.backend.model.response.LogoDetailsListResponse;
import pl.tchorzyksen.my.service.backend.service.LogoService;

import java.io.IOException;

import static pl.tchorzyksen.my.service.backend.model.response.LogoDetailsListResponse.LogoDetails;


@RestController
@AllArgsConstructor
@RequestMapping("/logo")
class LogoController {

  private final ResourceLoader resourceLoader;

  private final LogoService logoService;

  @GetMapping(value = "/applause", produces = MediaType.IMAGE_GIF_VALUE)
  public byte[] getApplause() throws IOException {
    return resourceLoader.getResource("classpath:logo/applause-leonardo-dicaprio.gif")
            .getInputStream()
            .readAllBytes();
  }

  @GetMapping(value = "/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
  public byte[] getLogo(@PathVariable String imageName) throws IOException {
    return logoService.fetchLogo(imageName);
  }

  @GetMapping
  public ResponseEntity<LogoDetailsListResponse> getLogoNameList() {
    return ResponseEntity.ok(mapToLogoDetailsListResponse(logoService.getLogoList()));
  }

  @PostMapping(consumes = {"multipart/form-data"})
  public void uploadLogo(@RequestParam MultipartFile image) throws IOException {
    var logo = Logo.builder()
            .imageName(image.getOriginalFilename())
            .contentLength(image.getSize())
            .content(image.getBytes())
            .contentType(image.getContentType())
            .build();
    logoService.saveLogo(logo);
  }

  private LogoDetailsListResponse mapToLogoDetailsListResponse(ListObjectsResult listObjectsResult) {
    var prefix = listObjectsResult.prefix();
    var logoDetails = listObjectsResult.objectSummaryList().stream()
            .map(objectSummary -> new LogoDetails(objectSummary.key().replace(prefix + "/", ""), objectSummary.lastModified())).toList();
    return new LogoDetailsListResponse(logoDetails);
  }

}
