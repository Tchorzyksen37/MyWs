package pl.tchorzyksen.my.web.service.controller;

import java.util.Set;
import java.util.stream.Collectors;
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
import pl.tchorzyksen.my.web.service.service.impl.BusinessUnitServiceImpl;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private BusinessUnitServiceImpl businessUnitService;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping
  public ResponseEntity<Set<UserResponse>> getUsers() {
    Set<UserResponse> userResponses = userService.getAllUsers().stream().map(this::mapToResponse).collect(Collectors.toSet());
    return ResponseEntity.ok(userResponses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {

    log.debug("Fetch user with id: {}", id);
    UserResponse userResponse = mapToResponse(userService.getUserById(id));

    return new ResponseEntity<>(userResponse, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {

    log.debug("Create user request {}", userRequest);

    UserDto userDto = mapToDto(userRequest);
    setBusinessUnitOnDto(userDto, userRequest.getBusinessUnitId());

    UserResponse userResponse = mapToResponse(userService.createUser(userDto));

    return new ResponseEntity<>(userResponse, HttpStatus.CREATED);

  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {

    log.debug("Update user with id: {} request {}", id, userRequest);
    UserDto userDto = mapToDto(userRequest);
    setBusinessUnitOnDto(userDto, userRequest.getBusinessUnitId());

    UserDto updatedUserDto = userService.updateUser(id, userDto);

    UserResponse userResponse = mapToResponse(updatedUserDto);

    return ResponseEntity.ok(userResponse);
  }

  @DeleteMapping
  public String deleteUser() {
    return "Delete user method invoked.";
  }

  private UserDto mapToDto(UserRequest userRequest) {
    log.debug("Map UserDto {} to UserResponse", userRequest);
    return modelMapper.map(userRequest, UserDto.class);
  }

  private UserResponse mapToResponse(UserDto userDto) {
    log.debug("Map UserDto {} to UserResponse", userDto);
    return modelMapper.map(userDto, UserResponse.class);
  }

  private void setBusinessUnitOnDto(UserDto userDto, Long businessUnitId) {

    if (businessUnitId != null) {
      userDto.setBusinessUnit(businessUnitService.getBusinessUnitById(businessUnitId));
    }
  }

}
