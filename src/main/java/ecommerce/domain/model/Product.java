package ecommerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a product entity within the e-commerce domain.
 *
 * <p>This class holds the core details of a sellable item, including its pricing, stock,
 * availability, and classification. It also encapsulates the business rule for deducting stock.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  /** The unique identifier for the product. */
  private Integer id;

  /** The display name of the product. */
  private String name;

  /** A detailed description of the product and its features. */
  private String description;

  /** The current selling price of the product. */
  private Double price;

  /** The current number of items available in inventory. */
  private Integer stock;

  /** The URL pointing to the main image of the product. */
  private String imageUrl;

  /** The category to which this product belongs. */
  private Category category;

  /**
   * Decreases the available stock of the product by the specified quantity.
   *
   * @param quantity the amount to deduct from the current stock.
   * @throws IllegalArgumentException if the requested quantity exceeds available stock.
   */
  public void decreaseStock(int quantity) {
    if (this.stock < quantity) {
      throw new IllegalArgumentException("Not enough stock for product: " + this.name);
    }
    this.stock -= quantity;
  }
}
