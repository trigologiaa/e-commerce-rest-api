package ecommerce.application.port.out;

import ecommerce.domain.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByEmail(String email);
}