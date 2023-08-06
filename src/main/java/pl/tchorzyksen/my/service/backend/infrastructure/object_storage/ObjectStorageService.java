package pl.tchorzyksen.my.service.backend.infrastructure.object_storage;

import java.io.InputStream;

public interface ObjectStorageService {

  void putObject(String key, InputStream inputStream);

}
