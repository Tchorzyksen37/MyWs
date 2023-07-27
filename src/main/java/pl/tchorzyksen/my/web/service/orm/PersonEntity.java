package pl.tchorzyksen.my.web.service.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString(callSuper = true)
@RequiredArgsConstructor
@Entity(name = "persons")
public class PersonEntity extends AbstractEntity<Long> {

  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 50)
  private String lastName;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    PersonEntity that = (PersonEntity) o;
    return this.getId() != null && Objects.equals(this.getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
