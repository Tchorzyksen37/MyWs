package pl.tchorzyksen.my.web.service.model;

import lombok.Data;

@Data
public class Address {

  private String street;

  private String postalCode;

  private String city;

  private String state;

  private Country countryCode;

}
