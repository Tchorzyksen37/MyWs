package pl.tchorzyksen.my.web.service.model.dto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class PersonDto {

  private Long id;

  private Long version;

  private ZonedDateTime createdDateTime;

  private ZonedDateTime lastModifiedDateTime;

  private String firstName;

  private String lastName;
}
