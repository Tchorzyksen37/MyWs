package pl.tchorzyksen.my.service.backend.infrastructure.object_storage;

import java.io.InputStream;
import java.util.Map;

public interface ObjectStorageService {

  void putObject(String key, InputStream inputStream, Map<String, Object> metadata);

}
