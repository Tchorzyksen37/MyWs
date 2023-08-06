package pl.tchorzyksen.my.service.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tchorzyksen.my.service.backend.infrastructure.object_storage.ObjectStorageService;
import pl.tchorzyksen.my.service.backend.model.Logo;

import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
public class LogoService {

  private final ObjectStorageService objectStorageService;

  public void saveLogo(Logo logo) {
    objectStorageService.putObject(logo.imageName(), new ByteArrayInputStream(logo.content()));
  }

}
