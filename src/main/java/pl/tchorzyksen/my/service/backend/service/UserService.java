package pl.tchorzyksen.my.service.backend.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tchorzyksen.my.service.backend.exception.BadRequestException;
import pl.tchorzyksen.my.service.backend.model.dto.UserDto;
import pl.tchorzyksen.my.service.backend.repositories.UserPersistenceService;

import java.util.ArrayList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  public static final String RESOURCE_NAME = "user";
  public static final String NEW_USER_CREATION_FAILED_EMAIL_ALREADY_EXIST = "Failed to create new user. Email <%s> address already exists.";
  private final UserPersistenceService userPersistenceService;

  public Page<UserDto> getAllUsers(Pageable pageable) {
    return userPersistenceService.fetchAll(pageable);
  }

  public UserDto createUser(UserDto userDto) {

    if (userPersistenceService.fetchByEmail(userDto.getEmail()) != null) {
      throw new BadRequestException(String.format(NEW_USER_CREATION_FAILED_EMAIL_ALREADY_EXIST,
              userDto.getEmail()));
    }

    return userPersistenceService.create(userDto);
  }

  public UserDto getUserByEmail(String email) {
    UserDto userDto = userPersistenceService.fetchByEmail(email);

    if (userDto == null) {
      throw new UsernameNotFoundException(email);
    }

    return userDto;
  }

  public UserDto getUserById(@NonNull Long userId) {
    log.debug("Fetch User with id: {}", userId);
    return userPersistenceService.fetchByUserId(userId);
  }

  public UserDto updateUser(UserDto userDto) {
    return userPersistenceService.update(userDto);
  }

  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    UserDto userDto = userPersistenceService.fetchByEmail(email);

    if (userDto == null) {
      throw new UsernameNotFoundException(email);
    }

    return new User(userDto.getEmail(), userDto.getEncryptedPassword(), new ArrayList<>());
  }

}
