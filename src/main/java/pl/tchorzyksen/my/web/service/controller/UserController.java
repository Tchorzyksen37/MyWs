package pl.tchorzyksen.my.web.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tchorzyksen.my.web.service.model.request.UserRequest;
import pl.tchorzyksen.my.web.service.model.response.UserResponse;
import pl.tchorzyksen.my.web.service.service.UserService;
import pl.tchorzyksen.my.web.service.model.dto.UserDto;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  private final ModelMapper modelMapper = new ModelMapper();

  @GetMapping
  public UserResponse getUser() {
    return new UserResponse();
  }

  @PostMapping
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {

    log.info("UserRequest {}", userRequest);

    UserDto userDto = modelMapper.map(userRequest, UserDto.class);

    log.debug("UserDto {}", userDto);

    return new ResponseEntity<>(
        modelMapper.map(userService.createUser(userDto), UserResponse.class), HttpStatus.CREATED);
  }

  @PutMapping
  public String updateUser() {
    return "Update user method invoked.";
  }

  @DeleteMapping
  public String deleteUser() {
    return "Delete user method invoked.";
  }
}
