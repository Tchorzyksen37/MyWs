package pl.tchorzyksen.my.web.service.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Getter;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@MappedSuperclass
abstract class AbstractModifiableEntity extends AbstractIdEntity<Long> {

  @Version
  @Column(name = "version")
  private Long version;

  @UpdateTimestamp
  @Column(name = "last_modified_date_time")
  private LocalDateTime lastModifiedDateTime;

}
