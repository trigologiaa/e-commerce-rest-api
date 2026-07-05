package ecommerce.domain.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a customer's purchase order within the domain.
 *
 * <p>An order aggregates multiple {@link OrderLine} items, tracks the overall status of the
 * transaction, and encapsulates the logic to calculate the total monetary amount.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  /** The unique identifier for the order. */
  private Integer id;

  /** The collection of individual items that make up this order. */
  private List<OrderLine> lines;

  /** The total calculated monetary value of the order. */
  private Double totalAmount;

  /** The current processing state of the order. */
  private String status;

  /**
   * Calculates the total amount of the order by summing the cost of all order lines.
   *
   * <p>The result is stored in the {@code totalAmount} field.
   */
  public void calculateTotal() {
    this.totalAmount =
        lines.stream().mapToDouble(line -> line.getPrice() * line.getQuantity()).sum();
  }
}
