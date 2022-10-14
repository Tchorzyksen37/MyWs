package pl.tchorzyksen.my.web.service.model.request;

import lombok.Data;
import pl.tchorzyksen.my.web.service.model.Address;

@Data
public class UserRequest {

  private Address address;

  private PersonRequest person;

  private String email;

  private String password;

  private Long businessUnitId;

}
