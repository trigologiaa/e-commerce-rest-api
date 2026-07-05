package ecommerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a product category within the e-commerce domain.
 *
 * <p>Used to logically group products together, making them easier to filter and discover.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
  /** The unique identifier for the category. */
  private Integer id;

  /** The display name of the category. */
  private String name;

  /** A brief description of what kind of products this category holds. */
  private String description;
}
