package pl.tchorzyksen.my.web.service.service.impl;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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
import pl.tchorzyksen.my.web.service.entities.UserModifiableEntity;
import pl.tchorzyksen.my.web.service.model.dto.UserDto;
import pl.tchorzyksen.my.web.service.repositories.BusinessUnitRepository;
import pl.tchorzyksen.my.web.service.repositories.UserRepository;
import pl.tchorzyksen.my.web.service.service.UserService;
import pl.tchorzyksen.my.web.service.shared.Utils;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

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
  public UserDto createUser(UserDto userDto) {

    if (userRepository.findUserByEmail(userDto.getEmail()) != null)
      throw new RuntimeException("Record already exists");

    UserModifiableEntity userEntity = modelMapper.map(userDto, UserModifiableEntity.class);

    if (userDto.getBusinessUnitId() != null) {
      userEntity.setBusinessUnitEntity(businessUnitRepository.findById(userDto.getBusinessUnitId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    String publicUserId = utils.generateUserId(30);
    userEntity.setUserId(publicUserId);
    userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

    log.info("UserDto {} is mapped to UserEntity: {}", userDto, userEntity);

    UserModifiableEntity storedUser = userRepository.save(userEntity);

    return modelMapper.map(storedUser, UserDto.class);
  }

  @Override
  public UserDto getUserByEmail(String email) {
    UserModifiableEntity userEntity = userRepository.findUserByEmail(email);

    if (userEntity == null) throw new UsernameNotFoundException(email);

    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userEntity, userDto);

    return userDto;
  }

  @Override
  public UserDto getUserById(Long id) {

    UserModifiableEntity userEntity = userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return modelMapper.map(userEntity, UserDto.class);
  }

  @Override
  public UserDto updateUser(Long id, UserDto userDto) {
    UserModifiableEntity userEntityInDb = userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    log.debug("Found UserEntity {}", userEntityInDb);

    modelMapper.map(userDto, userEntityInDb);
    if (userDto.getBusinessUnitId() != null) {
      BusinessUnitEntity businessUnitEntity = businessUnitRepository.findById(userDto.getBusinessUnitId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

      userEntityInDb.setBusinessUnitEntity(businessUnitEntity);
    }

    log.debug("UserEntity update with {}", userEntityInDb);

    UserModifiableEntity savedUserEntity = userRepository.save(userEntityInDb);

    log.debug("Saved UserEntity {}", savedUserEntity);

    return modelMapper.map(savedUserEntity, UserDto.class);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    UserModifiableEntity userEntity = userRepository.findUserByEmail(email);

    if (userEntity == null) throw new UsernameNotFoundException(email);

    return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
  }
}
