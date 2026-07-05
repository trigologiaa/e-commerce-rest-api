package ecommerce.infrastructure.adapter.out.db.repository;

import ecommerce.infrastructure.adapter.out.db.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA Repository interface for performing CRUD operations on {@link OrderEntity}.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, Integer> {}
