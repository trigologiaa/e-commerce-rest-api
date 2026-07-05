package ecommerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user entity within the e-commerce domain.
 *
 * <p>This core domain model encapsulates the essential details of a person interacting with the
 * system. It holds personal identification, contact information, security credentials, and the
 * designated access level ({@link Role}).
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  /** The unique identifier for the user in the system. */
  private Integer id;

  /** The user's given name (first name). */
  private String firstName;

  /** The user's family name (last name). */
  private String lastName;

  /** The user's email address, which also serves as a unique username for the authentication. */
  private String email;

  /** The user's encrypted password for security and login validation. */
  private String password;

  /**
   * The authorization role assigned to the user ({@code USER} or {@code ADMIN}), which dictates
   * their permissions across the aplicattion.
   */
  private Role role;
}
