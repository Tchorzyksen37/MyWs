package pl.tchorzyksen.my.service.backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@EqualsAndHashCode
public class Logo {
  private String imageName;
  private Long contentLength;
  @ToString.Exclude
  private byte[] content;
  private String contentType;
}

