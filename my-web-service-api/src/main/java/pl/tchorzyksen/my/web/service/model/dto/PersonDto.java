package pl.tchorzyksen.my.web.service.model.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PersonDto {

  private Long id;

  private Long version;

  private LocalDateTime createdDateTime;

  private LocalDateTime lastModifiedDateTime;

  private String firstName;

  private String lastName;
}
