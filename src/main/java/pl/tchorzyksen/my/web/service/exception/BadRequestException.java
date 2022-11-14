package pl.tchorzyksen.my.web.service.exception;

public class BadRequestException extends RuntimeException {

  public BadRequestException(String reason) {
    super(reason);
  }

}
