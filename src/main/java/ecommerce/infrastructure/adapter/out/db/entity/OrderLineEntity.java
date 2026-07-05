package ecommerce.infrastructure.adapter.out.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * JPA Entity representing the 'order_lines' table.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
@Entity
@Table(name = "order_lines")
public class OrderLineEntity {
  /** The unique primary key identifier for the order line record in the database. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** The unique identifier of the product associated with this specific order line. */
  @Column(name = "product_id")
  private Integer productId;

  /** The name of the product at the time the order was placed. */
  @Column(name = "product_name")
  private String productName;

  /** The number of units of the product purchased in this order line. */
  private Integer quantity;

  /** The price per unit of the product at the time the order was placed. */
  private Double price;
}
