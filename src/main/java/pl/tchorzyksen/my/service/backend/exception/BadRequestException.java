package pl.tchorzyksen.my.service.backend.exception;

public class BadRequestException extends RuntimeException {

  public BadRequestException(String reason) {
    super(reason);
  }

}
