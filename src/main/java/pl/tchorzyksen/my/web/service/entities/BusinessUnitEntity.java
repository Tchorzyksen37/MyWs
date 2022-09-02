package pl.tchorzyksen.my.web.service.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "business_units")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    uniqueConstraints = @UniqueConstraint(
        name = "name",
        columnNames = "name"
    )
)
public class BusinessUnitEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column
  private String name;

}
