package ecommerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  private Integer id;
  private String name;
  private String description;
  private Double price;
  private Integer stock;
  private String imageUrl;
  private Category category;

  public void decreaseStock(int quantity) {
    if (this.stock < quantity) {
      throw new IllegalArgumentException("Not enough stock for product: " + this.name);
    }
    this.stock -= quantity;
  }
}
