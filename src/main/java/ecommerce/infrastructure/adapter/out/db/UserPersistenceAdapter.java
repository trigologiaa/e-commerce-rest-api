package ecommerce.infrastructure.adapter.out.db;

import ecommerce.application.port.out.UserRepositoryPort;
import ecommerce.domain.model.Role;
import ecommerce.domain.model.User;
import ecommerce.infrastructure.adapter.out.db.entity.UserEntity;
import ecommerce.infrastructure.adapter.out.db.repository.SpringDataUserRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Secondary/Driven Adapter for user database persistence.
 *
 * <p>Implements the outbound repository port. Responsible for translating pure domain models into
 * JPA entities and interacting with the database.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Component
public class UserPersistenceAdapter implements UserRepositoryPort {
  /** The Spring Data JPA repository used for executing database operations for users. */
  private final SpringDataUserRepository repository;

  /**
   * Constructs a new UserPersistenceAdapter with the required repository dependency.
   *
   * @param repository the Spring Data repository for performing user CRUD operations.
   */
  public UserPersistenceAdapter(SpringDataUserRepository repository) {
    this.repository = repository;
  }

  /**
   * Persists a user domain entity to the database.
   *
   * <p>This method maps the pure domain {@link User} object to a JPA {@link UserEntity}, saves it
   * using the underlying Spring Data repository, and updates the original domain object with the
   * generated database identifier.
   *
   * @param user the domain user entity to be saved.
   * @return the saved domain user, populated with its generated database ID.
   */
  @Override
  public User save(User user) {
    UserEntity entity = new UserEntity();
    entity.setId(user.getId());
    entity.setFirstName(user.getFirstName());
    entity.setLastName(user.getLastName());
    entity.setEmail(user.getEmail());
    entity.setPassword(user.getPassword());
    entity.setRole(user.getRole().name());
    UserEntity saved = repository.save(entity);
    user.setId(saved.getId());
    return user;
  }

  /**
   * Retrieves a user domain entity based on their email address.
   *
   * <p>This method queries the database for a matching {@link UserEntity} and, if found, translates
   * it back into a pure domain {@link User} object.
   *
   * @param email the email address to search for.
   * @return an {@link Optional} containing the mapped domain user, or an empty Optional if no user
   *     is found.
   */
  @Override
  public Optional<User> findByEmail(String email) {
    return repository
        .findByEmail(email)
        .map(
            entity ->
                new User(
                    entity.getId(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getEmail(),
                    entity.getPassword(),
                    Role.valueOf(entity.getRole())));
  }
}

