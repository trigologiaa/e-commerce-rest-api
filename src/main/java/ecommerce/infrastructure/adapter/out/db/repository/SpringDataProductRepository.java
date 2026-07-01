package ecommerce.infrastructure.adapter.out.db.repository;

import ecommerce.infrastructure.adapter.out.db.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Integer> {}
