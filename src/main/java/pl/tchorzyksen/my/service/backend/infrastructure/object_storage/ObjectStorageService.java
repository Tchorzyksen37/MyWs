package pl.tchorzyksen.my.service.backend.infrastructure.object_storage;

import pl.tchorzyksen.my.service.backend.model.ListObjectsResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface ObjectStorageService {

  void putObject(String key, InputStream inputStream, Map<String, Object> metadata);

  ListObjectsResult getObjects(String userId);

  byte[] getObject(String key) throws IOException;

}
