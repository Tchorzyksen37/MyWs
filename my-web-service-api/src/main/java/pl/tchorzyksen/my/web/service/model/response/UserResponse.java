package pl.tchorzyksen.my.web.service.model.response;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Data;
import pl.tchorzyksen.my.web.service.model.Address;

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
