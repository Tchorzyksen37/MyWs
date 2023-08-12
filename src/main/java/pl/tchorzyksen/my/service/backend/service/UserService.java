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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tchorzyksen.my.service.backend.exception.BadRequestException;
import pl.tchorzyksen.my.service.backend.exception.ResourceNotFoundException;
import pl.tchorzyksen.my.service.backend.model.dto.UserDto;
import pl.tchorzyksen.my.service.backend.repositories.UserRepository;
import pl.tchorzyksen.my.service.backend.orm.UserEntity;
import pl.tchorzyksen.my.service.backend.shared.UserIdUtils;

import java.util.ArrayList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  public static final String RESOURCE_NAME = "user";

  public static final String NEW_USER_CREATION_FAILED_EMAIL_ALREADY_EXIST = "Failed to create new user. Email <%s> address already exists.";

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final ModelMapper modelMapper;

  public Page<UserDto> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable).map(this::mapToDto);
  }

  public UserDto createUser(UserDto userDto) {

    if (userRepository.findUserByEmail(userDto.getEmail()) != null) {
      throw new BadRequestException(String.format(NEW_USER_CREATION_FAILED_EMAIL_ALREADY_EXIST,
              userDto.getEmail()));
    }

    UserEntity userEntity = mapToEntity(userDto);
    userEntity.setUserId(UserIdUtils.generateUserId(30));
    userEntity.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));

    log.debug("Save UserEntity: {} to database", userEntity);

    return mapToDto(userRepository.save(userEntity));
  }

  public UserDto getUserByEmail(String email) {
    UserEntity userEntity = userRepository.findUserByEmail(email);

    if (userEntity == null) {
      throw new UsernameNotFoundException(email);
    }

    return mapToDto(userEntity);
  }

  public UserDto getUserById(@NonNull Long userId) {
    log.debug("Fetch User with id: {}", userId);
    return mapToDto(getUserEntity(userId));
  }

  public UserDto updateUser(Long id, UserDto userDto) {
    UserEntity userEntityInDb = getUserEntity(id);

    modelMapper.map(userDto, userEntityInDb);

    log.debug("Save UserEntity {}", userEntityInDb);
    UserEntity savedUserEntity = userRepository.save(userEntityInDb);

    return mapToDto(savedUserEntity);
  }

  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    UserEntity userEntity = userRepository.findUserByEmail(email);

    if (userEntity == null) {
      throw new UsernameNotFoundException(email);
    }

    return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
  }

  private UserEntity getUserEntity(Long id) {
    UserEntity userEntity = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, id));
    log.debug("UserEntity found {}", userEntity);
    return userEntity;
  }

  private UserEntity mapToEntity(UserDto userDto) {
    log.debug("Map UserDto {} to UserEntity", userDto);
    return modelMapper.map(userDto, UserEntity.class);
  }

  private UserDto mapToDto(UserEntity userEntity) {
    log.debug("Map UserEntity {} to UserDto", userEntity);
    return modelMapper.map(userEntity, UserDto.class);
  }

}
