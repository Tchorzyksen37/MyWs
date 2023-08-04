package pl.tchorzyksen.my.service.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import pl.tchorzyksen.my.service.backend.model.dto.UserDto;
import pl.tchorzyksen.my.service.backend.model.request.UserRequest;
import pl.tchorzyksen.my.service.backend.model.response.UserPageableResponse;
import pl.tchorzyksen.my.service.backend.model.response.UserResponse;
import pl.tchorzyksen.my.service.backend.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  private final ModelMapper modelMapper;

  @GetMapping
  public ResponseEntity<UserPageableResponse> getUsers(Pageable pageable) {
    log.debug("Get all users pageable {}", pageable);
    Page<UserResponse> userResponses = userService.getAllUsers(pageable).map(this::mapToResponse);
    return ResponseEntity.ok(new UserPageableResponse(pageable.getPageNumber(), userResponses.getTotalPages(),
            userResponses.getSize(), userResponses.toSet()));
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
    UserResponse userResponse = mapToResponse(userService.createUser(userDto));

    return new ResponseEntity<>(userResponse, HttpStatus.CREATED);

  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {

    log.debug("Update user with id: {} request {}", id, userRequest);
    UserDto userDto = mapToDto(userRequest);

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

}
