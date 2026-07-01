package ecommerce.infrastructure.adapter.in.web.dto;

import java.util.List;
import lombok.Data;

@Data
public class OrderRequestDto {
  private List<OrderLineDto> lines;

  @Data
  public static class OrderLineDto {
    private Integer productId;
    private Integer quantity;
  }
}
