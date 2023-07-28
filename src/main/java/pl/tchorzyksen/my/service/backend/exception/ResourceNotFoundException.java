package pl.tchorzyksen.my.service.backend.exception;

public class ResourceNotFoundException extends RuntimeException {

  public static final String MESSAGE_FORMAT = "Resource %s with id: %s not found.";

  public ResourceNotFoundException(String resourceName, Long id) {
    super(String.format(MESSAGE_FORMAT, resourceName, id));
  }

}
