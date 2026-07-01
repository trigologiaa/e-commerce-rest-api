package ecommerce.infrastructure.adapter.out.db.repository;

import ecommerce.infrastructure.adapter.out.db.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, Integer> {}
