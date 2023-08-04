package pl.tchorzyksen.my.service.backend.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonResponse {

  private Long id;

  private Long version;

  private LocalDateTime createdDateTime;

  private LocalDateTime lastModifiedDateTime;

  private String firstName;

  private String lastName;

}
