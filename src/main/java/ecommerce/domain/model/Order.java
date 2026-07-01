package ecommerce.domain.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  private Integer id;
  private List<OrderLine> lines;
  private Double totalAmount;
  private String status;

  public void calculateTotal() {
    this.totalAmount =
        lines.stream().mapToDouble(line -> line.getPrice() * line.getQuantity()).sum();
  }
}
