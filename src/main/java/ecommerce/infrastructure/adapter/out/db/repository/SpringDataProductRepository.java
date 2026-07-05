package ecommerce.infrastructure.adapter.out.db.repository;

import ecommerce.infrastructure.adapter.out.db.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA Repository interface for performing CRUD operations on {@link ProductEntity}.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Integer> {}
