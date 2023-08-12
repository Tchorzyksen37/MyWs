package pl.tchorzyksen.my.service.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.tchorzyksen.my.service.backend.model.dto.UserDto;
import pl.tchorzyksen.my.service.backend.service.UserService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade {

  private final UserService userService;

  private Optional<Authentication> getAuthentication() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
  }

  public UserDto getAuthenticatedUser() {
    var userEmail = (String) getAuthentication()
            .orElseThrow()
            .getPrincipal();
    return userService.getUserByEmail(userEmail);
  }

}
