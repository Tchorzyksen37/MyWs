package pl.tchorzyksen.my.web.service.orm;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

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

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = false;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    UserEntity that = (UserEntity) o;
    return this.getId() != null && Objects.equals(this.getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
