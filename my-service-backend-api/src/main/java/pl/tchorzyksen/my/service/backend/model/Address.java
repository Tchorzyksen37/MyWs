package pl.tchorzyksen.my.service.backend.model;

import lombok.Data;

@Data
public class Address {

  private String street;

  private String postalCode;

  private String city;

  private String state;

  private Country countryCode;

}
