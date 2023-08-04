package pl.tchorzyksen.my.service.backend.model.response;

import lombok.Data;
import pl.tchorzyksen.my.service.backend.model.Address;

import java.time.LocalDateTime;

@Data
public class UserResponse {

  private Long id;

  private Long version;

  private LocalDateTime createdDateTime;

  private LocalDateTime lastModifiedDateTime;

  private String userId;

  private PersonResponse person;

  private String email;

  private Address address;

}
