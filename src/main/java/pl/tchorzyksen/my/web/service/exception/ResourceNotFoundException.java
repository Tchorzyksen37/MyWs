package pl.tchorzyksen.my.web.service.exception;

public class ResourceNotFoundException extends RuntimeException {

  public static final String MESSAGE_FORMAT = "Resource %s with id: %s not found.";

  public ResourceNotFoundException(String resourceName, Long id) {
    super(String.format(MESSAGE_FORMAT, resourceName, id));
  }

}
