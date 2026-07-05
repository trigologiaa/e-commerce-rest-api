package ecommerce.infrastructure.adapter.out.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * JPA Entity representing the 'users' table in the database.
 *
 * <p>Mapped and managed by Hibernate. Used exclusively in the infrastructure layer to decouple the
 * database schema from the domain model.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
@Entity
@Table(name = "users")
public class UserEntity {
  /** The unique primary key identifier for the user record in the database. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** The user's given first name. */
  @Column(name = "first_name")
  private String firstName;

  /** The user's family or last name. */
  @Column(name = "last_name")
  private String lastName;

  /** The unique email address associated with the user, typically used as a login credential. */
  @Column(unique = true, nullable = false)
  private String email;

  /** The securely stored (and typically hashed) password for user authentication. */
  @Column(nullable = false)
  private String password;

  /** The authorization role or authority level assigned to the user (e.g., ADMIN, USER). */
  private String role;
}

