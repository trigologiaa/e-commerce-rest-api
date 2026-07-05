package ecommerce.infrastructure.adapter.in.web.dto;

import java.util.List;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for processing new order requests.
 *
 * <p>Carries the list of items a user wishes to purchase from the external client to the API.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
public class OrderRequestDto {
  /** The collection of individual items requested to be purchased in this order. */
  private List<OrderLineDto> lines;

  /** Nested DTO representing a single line item within an order request. */
  @Data
  public static class OrderLineDto {
    /** The unique identifier of the product being ordered. */
    private Integer productId;

    /** The requested number of units for the specified product. */
    private Integer quantity;
  }
}
