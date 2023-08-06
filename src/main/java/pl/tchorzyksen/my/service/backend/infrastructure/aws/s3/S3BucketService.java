package pl.tchorzyksen.my.service.backend.infrastructure.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.tchorzyksen.my.service.backend.infrastructure.object_storage.ObjectStorageService;

import java.io.InputStream;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
class S3BucketService implements ObjectStorageService {

  private final AmazonS3 amazonS3Client;

  @Value("${app.object-storage.buckets.logo-name}")
  private String bucketName;

  @Override
  public void putObject(String key, InputStream inputStream, Map<String, Object> metadata) {
    var objectMetadata = new ObjectMetadata();
    metadata.forEach(objectMetadata::setHeader);
    log.info("Upload file with key {} to s3 bucket {}", key, bucketName);
    amazonS3Client.putObject(bucketName, "key", inputStream, objectMetadata);
  }
}
