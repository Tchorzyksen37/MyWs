package pl.tchorzyksen.my.web.service.entities;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name = "users")
public class UserEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @OneToOne(cascade = CascadeType.ALL)
  private PersonEntity person;

  @ManyToOne
  @JoinColumn(name = "business_unit_id", referencedColumnName = "id")
  private BusinessUnitEntity businessUnitEntity;

  @Column(name = "email", nullable = false, length = 120, unique = true)
  private String email;

  @Column(name = "encrypted_password", nullable = false)
  private String encryptedPassword;

  @Column(name = "email_verification_token")
  private String emailVerificationToken;

  @Column(name = "email_verification_status", nullable = false)
  private Boolean emailVerificationStatus = false;
}
