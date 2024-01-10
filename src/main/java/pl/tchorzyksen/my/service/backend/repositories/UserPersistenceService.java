package pl.tchorzyksen.my.service.backend.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.tchorzyksen.my.service.backend.exception.ResourceNotFoundException;
import pl.tchorzyksen.my.service.backend.model.dto.UserDto;
import pl.tchorzyksen.my.service.backend.orm.UserEntity;
import pl.tchorzyksen.my.service.backend.shared.UserIdUtils;

import static pl.tchorzyksen.my.service.backend.service.UserService.RESOURCE_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPersistenceService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;

  public UserDto fetchByUserId(Long userId) {
    return userRepository.findById(userId)
            .map(this::mapToDto)
            .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, userId));
  }

  public UserDto fetchByEmail(String email) {
    return userRepository.findUserByEmail(email)
            .map(this::mapToDto)
            .orElse(null);
  }

  public Page<UserDto> fetchAll(Pageable pageable) {
    return userRepository.findAll(pageable)
            .map(this::mapToDto);
  }

  public UserDto create(UserDto userDto) {
    UserEntity userEntity = mapToEntity(userDto);
    userEntity.setUserId(UserIdUtils.generateUserId(30));
    userEntity.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
    return mapToDto(userRepository.save(userEntity));
  }

  public UserDto update(UserDto userDto) {
    UserEntity userEntityInDb = userRepository.findById(userDto.getId())
            .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, userDto.getId()));
    modelMapper.map(userDto, userEntityInDb);
    return mapToDto(userRepository.save(userEntityInDb));
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
