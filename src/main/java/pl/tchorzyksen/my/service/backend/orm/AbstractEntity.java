package pl.tchorzyksen.my.service.backend.orm;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@ToString
@MappedSuperclass
abstract class AbstractEntity<T extends Serializable> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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
    createdDateTime = LocalDateTime.now();
    lastModifiedDateTime = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    lastModifiedDateTime = LocalDateTime.now();
  }

}
