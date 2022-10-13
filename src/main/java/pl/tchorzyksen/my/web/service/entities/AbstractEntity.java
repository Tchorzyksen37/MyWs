package pl.tchorzyksen.my.web.service.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@MappedSuperclass
abstract class AbstractEntity {

  @Id
  @GeneratedValue
  private Long id;

  @CreationTimestamp
  @Column(name = "created_date_time")
  private LocalDateTime createdDateTime;

  @UpdateTimestamp
  @Column(name = "last_modified_date_time")
  private LocalDateTime lastModifiedDateTime;

}
