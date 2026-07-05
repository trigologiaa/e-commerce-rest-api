package ecommerce.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for Product creation and updates.
 *
 * <p>Carries data between external clients and the REST controllers, decoupling the API schema from
 * the internal domain models. Includes Jakarta Validation constraints.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
public class ProductDto {

  /**
   * The unique identifier for the product, typically null during creation and populated for
   * updates.
   */
  private Integer id;

  /** The display name of the product. */
  @NotBlank(message = "Name is mandatory")
  private String name;

  /** A detailed description of the product and its features. */
  @NotBlank(message = "Description is mandatory")
  private String description;

  /** The intended selling price of the product. */
  @NotNull
  @Min(value = 1, message = "Price must be greater than zero")
  private Double price;

  /** The current number of items available or to be added in inventory. */
  @NotNull
  @Min(value = 0, message = "Stock cannot be negative")
  private Integer stock;

  /** The URL pointing to the main image of the product. */
  private String imageUrl;
}
