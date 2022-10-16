package pl.tchorzyksen.my.web.service.service.impl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pl.tchorzyksen.my.web.service.entities.BusinessUnitEntity;
import pl.tchorzyksen.my.web.service.entities.UserEntity;
import pl.tchorzyksen.my.web.service.exceptions.BadRequestException;
import pl.tchorzyksen.my.web.service.exceptions.ResourceNotFoundException;
import pl.tchorzyksen.my.web.service.model.dto.UserDto;
import pl.tchorzyksen.my.web.service.repositories.BusinessUnitRepository;
import pl.tchorzyksen.my.web.service.repositories.UserRepository;
import pl.tchorzyksen.my.web.service.service.UserService;
import pl.tchorzyksen.my.web.service.shared.Utils;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

  private static final String RESOURCE_NAME = "user";

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BusinessUnitRepository businessUnitRepository;

  @Autowired
  private Utils utils;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public Set<UserDto> getAllUsers() {
    return userRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toSet());
  }

  @Override
  public UserDto createUser(UserDto userDto) {

    if (userRepository.findUserByEmail(userDto.getEmail()) != null) {
      throw new BadRequestException(String.format("Failed to create new user. Email <%s> address already exists.",
          userDto.getEmail()));
    }

    UserEntity userEntity = mapToEntity(userDto);
    userEntity.setUserId(utils.generateUserId(30));
    userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

    log.debug("Save UserEntity: {} to database", userEntity);

    return mapToDto(userRepository.save(userEntity));
  }

  @Override
  public UserDto getUserByEmail(String email) {
    UserEntity userEntity = userRepository.findUserByEmail(email);

    if (userEntity == null) {
      throw new UsernameNotFoundException(email);
    }

    return mapToDto(userEntity);
  }

  @Override
  public UserDto getUserById(@NonNull Long userId) {
    log.debug("Fetch User with id: {}", userId);
    return mapToDto(getUserEntity(userId));
  }

  @Override
  public UserDto updateUser(Long id, UserDto userDto) {
    UserEntity userEntityInDb = getUserEntity(id);

    modelMapper.map(userDto, userEntityInDb);

    log.debug("Save UserEntity {}", userEntityInDb);
    UserEntity savedUserEntity = userRepository.save(userEntityInDb);

    return mapToDto(savedUserEntity);
  }

  @Override
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
