package ecommerce.infrastructure.adapter.out.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * JPA Entity representing the 'products' table in the database.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
@Entity
@Table(name = "products")
public class ProductEntity {
  /** The unique primary key identifier for the product record in the database. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** The display name of the product. */
  private String name;

  /** A detailed description of the product and its features. */
  private String description;

  /** The current selling price of the product. */
  private Double price;

  /** The current number of items available in inventory. */
  private Integer stock;

  /** The URL pointing to the main image of the product. */
  @Column(name = "image_url")
  private String imageUrl;
}
