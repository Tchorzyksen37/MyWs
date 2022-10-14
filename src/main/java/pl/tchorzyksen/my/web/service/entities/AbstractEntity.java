package pl.tchorzyksen.my.web.service.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import static java.time.LocalDateTime.now;

@Getter
@ToString
@MappedSuperclass
abstract class AbstractEntity<T extends Serializable> {

  @Id
  @GeneratedValue
  private T id;

  @Version
  @Column(name = "version")
  private Long version;

  @CreatedDate
  @Column(name = "created_date_time")
  private LocalDateTime createdDateTime;

  @LastModifiedDate
  @Column(name = "last_modified_date_time")
  private LocalDateTime lastModifiedDateTime;

  @PrePersist
  public void prePersist() {
    createdDateTime = now();
    lastModifiedDateTime = now();
  }

  @PreUpdate
  public void preUpdate() {
    lastModifiedDateTime = now();
  }

}
