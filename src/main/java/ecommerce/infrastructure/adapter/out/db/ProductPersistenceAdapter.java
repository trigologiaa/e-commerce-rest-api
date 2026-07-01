package ecommerce.infrastructure.adapter.out.db;

import ecommerce.application.port.out.ProductRepositoryPort;
import ecommerce.domain.model.Product;
import ecommerce.infrastructure.adapter.out.db.entity.ProductEntity;
import ecommerce.infrastructure.adapter.out.db.repository.SpringDataProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ProductPersistenceAdapter implements ProductRepositoryPort {
  private final SpringDataProductRepository repository;

  public ProductPersistenceAdapter(SpringDataProductRepository repository) {
    this.repository = repository;
  }

  @Override
  public Product save(Product product) {
    ProductEntity entity = this.mapToEntity(product);
    ProductEntity saved = this.repository.save(entity);
    return this.mapToDomain(saved);
  }

  @Override
  public Optional<Product> findById(Integer id) {
    return this.repository.findById(id).map(this::mapToDomain);
  }

  @Override
  public List<Product> findAll() {
    return this.repository.findAll().stream().map(this::mapToDomain).collect(Collectors.toList());
  }

  @Override
  public List<Product> findAllById(List<Integer> ids) {
    return this.repository.findAllById(ids).stream()
        .map(this::mapToDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteById(Integer id) {
    this.repository.deleteById(id);
  }

  private Product mapToDomain(ProductEntity entity) {
    return new Product(
        entity.getId(),
        entity.getName(),
        entity.getDescription(),
        entity.getPrice(),
        entity.getStock(),
        entity.getImageUrl(),
        null);
  }

  private ProductEntity mapToEntity(Product product) {
    ProductEntity entity = new ProductEntity();
    entity.setId(product.getId());
    entity.setName(product.getName());
    entity.setDescription(product.getDescription());
    entity.setPrice(product.getPrice());
    entity.setStock(product.getStock());
    entity.setImageUrl(product.getImageUrl());
    return entity;
  }
}
