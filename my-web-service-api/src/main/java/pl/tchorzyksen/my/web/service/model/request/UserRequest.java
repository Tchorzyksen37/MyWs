package pl.tchorzyksen.my.web.service.model.request;

import lombok.Data;

@Data
public class UserRequest {

  private PersonRequest person;

  private String email;

  private String password;
}
