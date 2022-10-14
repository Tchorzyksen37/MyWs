package pl.tchorzyksen.my.web.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResourceNotFoundException extends ResponseStatusException {

  private static final String MESSAGE_FORMAT = "Resource %s with id: %s not found.";

  public ResourceNotFoundException(String resourceName, Long id) {
    super(HttpStatus.NOT_FOUND, String.format(MESSAGE_FORMAT, resourceName, id));
  }

}
