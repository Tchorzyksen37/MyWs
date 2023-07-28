package pl.tchorzyksen.my.service.backend.model.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class BusinessUnitDto {

  private Long id;

  private Long version;

  private ZonedDateTime createdDateTime;

  private ZonedDateTime lastModifiedDateTime;

  private String name;

}
