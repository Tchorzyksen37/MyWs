package pl.tchorzyksen.my.web.service.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "users")
public class UserEntity extends AbstractEntity implements Serializable {

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
