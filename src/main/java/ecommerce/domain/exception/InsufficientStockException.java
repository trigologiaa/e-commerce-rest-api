package ecommerce.domain.exception;

/**
 * Exception thrown when an attempt is made to order a quantity of a product that exceeds its
 * currently available stock.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public class InsufficientStockException extends DomainException {
  /**
   * Constructs a new InsufficientStockException with the specified detail message.
   *
   * @param message the detail message explaining the specific stock issue.
   */
  public InsufficientStockException(String message) {
    super(message);
  }
}
