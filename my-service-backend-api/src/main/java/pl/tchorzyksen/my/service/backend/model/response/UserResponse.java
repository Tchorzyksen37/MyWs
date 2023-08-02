package pl.tchorzyksen.my.service.backend.model.response;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import lombok.Data;
import pl.tchorzyksen.my.service.backend.model.Address;

@Data
public class UserResponse {

  private Long id;

  private Long version;

  private LocalDateTime createdDateTime;

  private LocalDateTime lastModifiedDateTime;

  private String userId;

  private PersonResponse person;

  private BusinessUnitResponse businessUnit;

  private String email;

  private Address address;

}
