package pl.tchorzyksen.my.web.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tchorzyksen.my.web.service.model.dto.UserDto;
import pl.tchorzyksen.my.web.service.model.request.UserRequest;
import pl.tchorzyksen.my.web.service.model.response.UserResponse;
import pl.tchorzyksen.my.web.service.service.UserService;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  private final ModelMapper modelMapper = new ModelMapper();

  {
    var typeMap = modelMapper.typeMap(UserRequest.class, UserDto.class);
    typeMap.addMappings(mapper -> mapper.skip(UserDto::setId));
    typeMap.addMappings(mapper -> mapper.skip(UserDto::setUserId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
    UserDto userDto = userService.getUserById(id);

    return new ResponseEntity<>(
        modelMapper.map(userDto, UserResponse.class), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {

    log.info("UserRequest {}", userRequest);

    UserDto userDto = modelMapper.map(userRequest, UserDto.class);

    log.debug("UserDto {}", userDto);

    return new ResponseEntity<>(
        modelMapper.map(userService.createUser(userDto), UserResponse.class), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {

    log.debug("UserRequest {}", userRequest);
    UserDto userDto = modelMapper.map(userRequest, UserDto.class);
    log.debug("UserDto {}", userDto);
    UserDto updatedUserDto = userService.updateUser(id, userDto);
    log.debug("Updated UserDto {}", updatedUserDto);
    UserResponse userResponse = modelMapper.map(updatedUserDto, UserResponse.class);
    log.debug("User Response {}", userResponse);
    return ResponseEntity.ok(userResponse);
  }

  @DeleteMapping
  public String deleteUser() {
    return "Delete user method invoked.";
  }
}
