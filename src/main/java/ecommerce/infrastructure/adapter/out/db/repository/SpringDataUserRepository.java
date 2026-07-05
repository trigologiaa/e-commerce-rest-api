package ecommerce.infrastructure.adapter.out.db.repository;

import ecommerce.infrastructure.adapter.out.db.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA Repository interface for performing CRUD operations on {@link UserEntity}.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public interface SpringDataUserRepository extends JpaRepository<UserEntity, Integer> {
  /**
   * Retrieves a user entity based on their unique email address.
   *
   * @param email the email address to search for in the database.
   * @return an {@link Optional} containing the found user entity, or an empty Optional if no
   *     matching user is found.
   */
  Optional<UserEntity> findByEmail(String email);
}

