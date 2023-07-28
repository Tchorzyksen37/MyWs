package pl.tchorzyksen.my.service.backend.model.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.Data;
import pl.tchorzyksen.my.service.backend.model.Address;

@Data
public class UserDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;

  private Long version;

  private ZonedDateTime createdDateTime;

  private ZonedDateTime lastModifiedDateTime;

  private String userId;

  private Address address;

  private PersonDto person;

  private BusinessUnitDto businessUnit;

  private String email;

  private String password;

  private String encryptedPassword;

  private String emailVerificationToken;

  private Boolean emailVerificationStatus = false;

  private Boolean isActive = false;
}
