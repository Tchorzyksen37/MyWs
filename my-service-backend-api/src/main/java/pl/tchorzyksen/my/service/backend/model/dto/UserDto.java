package pl.tchorzyksen.my.service.backend.model.dto;

import lombok.Data;
import pl.tchorzyksen.my.service.backend.model.Address;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;

  private Long version;

  private LocalDateTime createdDateTime;

  private LocalDateTime lastModifiedDateTime;

  private String userId;

  private Address address;

  private PersonDto person;

  private String email;

  private String password;

  private String encryptedPassword;

  private String emailVerificationToken;

  private Boolean emailVerificationStatus = false;

  private Boolean isActive = false;
}
