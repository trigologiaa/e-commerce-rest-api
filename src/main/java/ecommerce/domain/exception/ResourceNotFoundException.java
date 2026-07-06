package ecommerce.domain.exception;

/**
 * Exception thrown when a requested domain entity (e.g., Product, User, Order) cannot be found in
 * the persistence layer.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public class ResourceNotFoundException extends DomainException {
  /** The unique identifier for this version of the serialized class. */
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new ResourceNotFoundException with the specified detail message.
   *
   * @param message the detail message indicating which resource could not be found.
   */
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
