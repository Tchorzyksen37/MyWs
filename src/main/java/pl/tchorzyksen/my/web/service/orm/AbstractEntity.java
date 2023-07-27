package pl.tchorzyksen.my.web.service.orm;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.ZonedDateTime;

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
  private ZonedDateTime createdDateTime;

  @LastModifiedDate
  @Column(name = "last_modified_date_time")
  private ZonedDateTime lastModifiedDateTime;

  @PrePersist
  public void prePersist() {
    createdDateTime = ZonedDateTime.now();
    lastModifiedDateTime = ZonedDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    lastModifiedDateTime = ZonedDateTime.now();
  }

}
