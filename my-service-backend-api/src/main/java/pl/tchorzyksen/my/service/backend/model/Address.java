package pl.tchorzyksen.my.service.backend.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Address {

  private Long id;

  private Long version;

  private LocalDateTime createdDateTime;

  private LocalDateTime lastModifiedDateTime;

  private String street;

  private String postalCode;

  private String city;

  private String state;

  private Country countryCode;

}
