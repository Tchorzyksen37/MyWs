package pl.tchorzyksen.my.web.service.model;

import lombok.Getter;

@Getter
public enum Country {
  PL("Poland");

  private final String countryName;

  Country(String countryName) {
    this.countryName = countryName;
  }
}
