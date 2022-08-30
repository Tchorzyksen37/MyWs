package pl.tchorzyksen.my.web.service.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {

  private String email;

  private String password;
}
