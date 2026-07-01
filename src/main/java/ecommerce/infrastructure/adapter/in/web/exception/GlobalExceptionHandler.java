package ecommerce.infrastructure.adapter.in.web.exception;

import ecommerce.domain.exception.InsufficientStockException;
import ecommerce.domain.exception.ResourceNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException exception) {
    return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InsufficientStockException.class)
  public ResponseEntity<Map<String, String>> handleStock(InsufficientStockException exception) {
    return buildResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    exception
        .getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  private ResponseEntity<Map<String, String>> buildResponse(String message, HttpStatus status) {
    Map<String, String> response = new HashMap<>();
    response.put("error", message);
    return ResponseEntity.status(status).body(response);
  }
}
