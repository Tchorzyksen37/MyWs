package pl.tchorzyksen.my.service.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.tchorzyksen.my.service.backend.model.dto.UserDto;

public interface UserService extends UserDetailsService {

  UserDto createUser(UserDto userDto);

  Page<UserDto> getAllUsers(Pageable pageable);

  UserDto getUserByEmail(String email);

  UserDto getUserById(Long id);

  UserDto updateUser(Long id, UserDto userDto);
}
