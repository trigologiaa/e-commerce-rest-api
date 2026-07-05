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

/**
 * Global aspect class for handling and translating exceptions.
 *
 * <p>Intercepts unhandled domain exceptions and validation errors thrown during HTTP requests,
 * translating them into structured JSON error responses with appropriate HTTP status codes.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
  /**
   * Intercepts and handles {@link ResourceNotFoundException}.
   *
   * @param exception the domain exception indicating a missing resource.
   * @return a {@link ResponseEntity} containing the error message and an HTTP 404 Not Found status.
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException exception) {
    return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Intercepts and handles {@link InsufficientStockException}.
   *
   * @param exception the domain exception indicating a business rule violation regarding stock.
   * @return a {@link ResponseEntity} containing the error message and an HTTP 400 Bad Request
   *     status.
   */
  @ExceptionHandler(InsufficientStockException.class)
  public ResponseEntity<Map<String, String>> handleStock(InsufficientStockException exception) {
    return buildResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Intercepts and handles validation errors triggered by {@code @Valid} annotations on request
   * bodies.
   *
   * <p>Extracts all field-specific validation errors and structures them into a clear key-value
   * map.
   *
   * @param exception the exception containing the binding and validation results.
   * @return a {@link ResponseEntity} containing a map of invalid fields and their corresponding
   *     error messages, with an HTTP 400 Bad Request status.
   */
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

  /**
   * Helper method to construct a standardized JSON response format for single error messages.
   *
   * @param message the detail message to include in the error response.
   * @param status the HTTP status code to be returned to the client.
   * @return a fully constructed {@link ResponseEntity} ready to be sent to the client.
   */
  private ResponseEntity<Map<String, String>> buildResponse(String message, HttpStatus status) {
    Map<String, String> response = new HashMap<>();
    response.put("error", message);
    return ResponseEntity.status(status).body(response);
  }
}
