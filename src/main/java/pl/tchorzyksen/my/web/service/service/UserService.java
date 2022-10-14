package pl.tchorzyksen.my.web.service.service;

import java.util.Set;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.tchorzyksen.my.web.service.model.dto.UserDto;

public interface UserService extends UserDetailsService {

  UserDto createUser(UserDto userDto);

  Set<UserDto> getAllUsers();

  UserDto getUserByEmail(String email);

  UserDto getUserById(Long id);

  UserDto updateUser(Long id, UserDto userDto);
}
