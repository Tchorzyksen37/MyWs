package pl.tchorzyksen.my.service.backend.model.request;

import lombok.Data;
import pl.tchorzyksen.my.service.backend.model.Address;

@Data
public class UserRequest {

  private Address address;

  private PersonRequest person;

  private String email;

  private String password;

}
