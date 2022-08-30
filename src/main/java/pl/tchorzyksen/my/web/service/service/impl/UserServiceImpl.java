package pl.tchorzyksen.my.web.service.service.impl;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tchorzyksen.my.web.service.entities.UserEntity;
import pl.tchorzyksen.my.web.service.model.dto.UserDto;
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
  private Utils utils;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  private final ModelMapper modelMapper = new ModelMapper();

  @Override
  public UserDto createUser(UserDto userDto) {

    if (userRepository.findUserByEmail(userDto.getEmail()) != null)
      throw new RuntimeException("Record already exists");

    log.info("UserDto {}", userDto);
    UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
    log.info("UserEntity: {}", userEntity);

    String publicUserId = utils.generateUserId(30);
    userEntity.setUserId(publicUserId);
    userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

    UserEntity storedUser = userRepository.save(userEntity);

    return modelMapper.map(storedUser, UserDto.class);
  }

  @Override
  public UserDto getUser(String email) {
    UserEntity userEntity = userRepository.findUserByEmail(email);

    if (userEntity == null) throw new UsernameNotFoundException(email);

    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userEntity, userDto);

    return userDto;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    UserEntity userEntity = userRepository.findUserByEmail(email);

    if (userEntity == null) throw new UsernameNotFoundException(email);

    return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
  }
}
