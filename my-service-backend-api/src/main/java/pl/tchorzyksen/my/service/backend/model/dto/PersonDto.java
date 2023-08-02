package pl.tchorzyksen.my.service.backend.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Data
public class PersonDto {

  private Long id;

  private Long version;

  private LocalDateTime createdDateTime;

  private LocalDateTime lastModifiedDateTime;

  private String firstName;

  private String lastName;
}
