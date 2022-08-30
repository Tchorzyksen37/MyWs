package pl.tchorzyksen.my.web.service.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.tchorzyksen.my.web.service.model.dto.UserDto;

public interface UserService extends UserDetailsService {

  UserDto createUser(UserDto userDto);

  UserDto getUserByEmail(String email);

  UserDto getUserById(Long id);
}
