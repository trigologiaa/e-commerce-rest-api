package ecommerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a single line item within an {@link Order}.
 *
 * <p>It captures a snapshot of the product details at the time of purchase, including the
 * historical price and requested quantity, ensuring that future price changes do not affect past
 * orders.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {
  /** The ID of the purchased product. */
  private Integer productId;

  /** The historical name of the product at the time of purchase. */
  private String productName;

  /** The quantity of the product ordered. */
  private Integer quantity;

  /** The historical price of the product at the time of purchase. */
  private Double price;
}
