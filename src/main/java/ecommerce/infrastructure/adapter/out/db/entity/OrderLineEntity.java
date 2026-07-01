package ecommerce.infrastructure.adapter.out.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "order_lines")
public class OrderLineEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "product_id")
  private Integer productId;

  @Column(name = "product_name")
  private String productName;

  private Integer quantity;
  private Double price;
}
