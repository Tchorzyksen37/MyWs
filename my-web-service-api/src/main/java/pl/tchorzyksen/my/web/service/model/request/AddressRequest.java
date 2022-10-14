package pl.tchorzyksen.my.web.service.model.request;

import lombok.Data;
import pl.tchorzyksen.my.web.service.model.Country;

@Data
public class AddressRequest {

  private String street;

  private String postalCode;

  private String city;

  private String state;

  private Country countryCode;

}
