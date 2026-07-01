package ecommerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {
  private Integer productId;
  private String productName;
  private Integer quantity;
  private Double price;
}
