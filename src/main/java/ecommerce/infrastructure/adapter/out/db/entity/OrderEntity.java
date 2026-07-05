package ecommerce.infrastructure.adapter.out.db.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

/**
 * JPA Entity representing the 'orders' table.
 *
 * <p>Maps the relationship between an order and its associated lines.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
@Entity
@Table(name = "orders")
public class OrderEntity {
  /** The unique primary key identifier for the order record in the database. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** The total calculated monetary value of the order. */
  @Column(name = "total_amount")
  private Double totalAmount;

  /** The current processing state of the order (e.g., PENDING, SHIPPED, DELIVERED). */
  private String status;

  /**
   * The collection of individual items that make up this order.
   *
   * <p>Configured with cascade and orphan removal to fully manage the lifecycle of the order lines.
   */
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "order_id")
  private List<OrderLineEntity> lines;
}
