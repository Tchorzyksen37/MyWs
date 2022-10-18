package pl.tchorzyksen.my.web.service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.tchorzyksen.my.web.service.model.dto.UserDto;

public interface UserService extends UserDetailsService {

  UserDto createUser(UserDto userDto);

  Page<UserDto> getAllUsers(Pageable pageable);

  UserDto getUserByEmail(String email);

  UserDto getUserById(Long id);

  UserDto updateUser(Long id, UserDto userDto);
}
