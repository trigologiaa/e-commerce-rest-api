package ecommerce.application.port.out;

import ecommerce.domain.model.User;
import java.util.Optional;

/**
 * Outbound port (SPI) for user persistence.
 *
 * <p>Defines the contract to store and retrieve {@link User} entities, essential for authentication
 * and authorization flows.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public interface UserRepositoryPort {
  /**
   * Persists a given user entity to the underlying storage.
   *
   * @param user the user entity to be saved.
   * @return the persisted user, potentially updated with database-generated fields (like an ID).
   */
  User save(User user);

  /**
   * Retrieves a user by their unique email address.
   *
   * @param email the email address of the user to search for.
   * @return an {@link Optional} containing the found user, or an empty Optional if no user matches
   *     the email.
   */
  Optional<User> findByEmail(String email);
}

