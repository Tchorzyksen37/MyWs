package pl.tchorzyksen.my.service.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tchorzyksen.my.service.backend.infrastructure.object_storage.ObjectStorageService;
import pl.tchorzyksen.my.service.backend.model.ListObjectsResult;
import pl.tchorzyksen.my.service.backend.model.Logo;
import pl.tchorzyksen.my.service.backend.security.AuthenticationFacade;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import static com.amazonaws.services.s3.Headers.CONTENT_LENGTH;
import static com.amazonaws.services.s3.Headers.CONTENT_TYPE;

@Service
@RequiredArgsConstructor
public class LogoService {

  private final ObjectStorageService objectStorageService;

  private final AuthenticationFacade authenticationFacade;

  public byte[] fetchLogo(String logoName) throws IOException {
    var authenticatedUser = authenticationFacade.getAuthenticatedUser();
    var key = generateKey(authenticatedUser.getUserId(), logoName);
    return objectStorageService.getObject(key);
  }

  public ListObjectsResult getLogoList() {
    var authenticatedUser = authenticationFacade.getAuthenticatedUser();
    return objectStorageService.getObjects(authenticatedUser.getUserId());
  }

  public void saveLogo(Logo logo) {
    var authenticatedUser = authenticationFacade.getAuthenticatedUser();
    var key = generateKey(authenticatedUser.getUserId(), logo.imageName());
    objectStorageService.putObject(key, new ByteArrayInputStream(logo.content()),
            Map.of(CONTENT_TYPE, logo.contentType(), CONTENT_LENGTH, logo.contentLength()));
  }

  private String generateKey(String userId, String imageName) {
    return String.format("%s/%s", userId, imageName);
  }

}
