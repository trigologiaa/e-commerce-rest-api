package ecommerce.infrastructure.adapter.out.db;

import ecommerce.application.port.out.UserRepositoryPort;
import ecommerce.domain.model.Role;
import ecommerce.domain.model.User;
import ecommerce.infrastructure.adapter.out.db.entity.UserEntity;
import ecommerce.infrastructure.adapter.out.db.repository.SpringDataUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final SpringDataUserRepository repository;

    public UserPersistenceAdapter(SpringDataUserRepository repository) {
        this.repository = repository;
    }

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

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(entity -> 
            new User(entity.getId(), entity.getFirstName(), entity.getLastName(), 
                     entity.getEmail(), entity.getPassword(), Role.valueOf(entity.getRole()))
        );
    }
}