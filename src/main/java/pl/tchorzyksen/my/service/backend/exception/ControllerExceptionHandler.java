package pl.tchorzyksen.my.service.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.tchorzyksen.my.service.backend.exception.model.ExceptionResponse;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(resourceNotFoundException.getMessage()));
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(BadRequestException badRequestException) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(badRequestException.getMessage()));
  }

}
