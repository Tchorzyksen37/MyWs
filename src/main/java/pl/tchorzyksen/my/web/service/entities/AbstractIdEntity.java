package pl.tchorzyksen.my.web.service.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@MappedSuperclass
abstract class AbstractIdEntity<T extends Serializable> {

  @Id
  @GeneratedValue
  private T id;

  @CreationTimestamp
  @Column(name = "created_date_time")
  private LocalDateTime createdDateTime;

}
