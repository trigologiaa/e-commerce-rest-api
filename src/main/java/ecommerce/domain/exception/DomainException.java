package ecommerce.domain.exception;

/**
 * Base abstract exception for all business logic and domain-related errors.
 *
 * <p>Extending this class ensuring that application-specific errors can be caught and handled
 * gracefully at the infrastructure layer without exposing internal logic.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public class DomainException extends RuntimeException {
  /** The unique identifier for this version of the serialized class. */
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new DomainException with the specified detail message.
   *
   * @param message the detail message explaining the reason for the exception.
   */
  public DomainException(String message) {
    super(message);
  }
}
