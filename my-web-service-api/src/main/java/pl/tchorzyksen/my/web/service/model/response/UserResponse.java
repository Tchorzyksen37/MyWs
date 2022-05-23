package pl.tchorzyksen.my.web.service.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

  private String userId;

  private PersonResponse person;

  private String email;
}
