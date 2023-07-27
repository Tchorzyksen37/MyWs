package pl.tchorzyksen.my.web.service.model.response;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.Data;
import pl.tchorzyksen.my.web.service.model.Address;

@Data
public class UserResponse {

  private Long id;

  private Long version;

  private ZonedDateTime createdDateTime;

  private ZonedDateTime lastModifiedDateTime;

  private String userId;

  private PersonResponse person;

  private BusinessUnitResponse businessUnit;

  private String email;

  private Address address;

}
