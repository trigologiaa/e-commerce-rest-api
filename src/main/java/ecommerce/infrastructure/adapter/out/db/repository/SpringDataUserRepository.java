package ecommerce.infrastructure.adapter.out.db.repository;

import ecommerce.infrastructure.adapter.out.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
}