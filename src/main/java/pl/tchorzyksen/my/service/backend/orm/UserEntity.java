package pl.tchorzyksen.my.service.backend.orm;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString(callSuper = true)
@RequiredArgsConstructor
@Entity(name = "users")
public class UserEntity extends AbstractEntity<Long> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @OneToOne(cascade = CascadeType.ALL)
  private AddressEntity address;

  @OneToOne(cascade = CascadeType.ALL)
  private PersonEntity person;

  @Column(name = "email", nullable = false, length = 120, unique = true)
  private String email;

  @Column(name = "encrypted_password", nullable = false)
  private String encryptedPassword;

  @Column(name = "email_verification_token")
  private String emailVerificationToken;

  @Column(name = "email_verification_status", nullable = false)
  private Boolean emailVerificationStatus = false;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = false;

}
