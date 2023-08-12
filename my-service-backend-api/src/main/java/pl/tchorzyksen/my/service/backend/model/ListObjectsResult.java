package pl.tchorzyksen.my.service.backend.model;

import java.time.LocalDateTime;
import java.util.List;

public record ListObjectsResult(String bucketName, String prefix, List<ObjectSummary> objectSummaryList) {

  public record ObjectSummary(String key, LocalDateTime lastModified) {

  }
}
