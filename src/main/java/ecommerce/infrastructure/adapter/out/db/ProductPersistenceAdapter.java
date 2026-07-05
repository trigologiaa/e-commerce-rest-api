package ecommerce.infrastructure.adapter.out.db;

import ecommerce.application.port.out.ProductRepositoryPort;
import ecommerce.domain.model.Product;
import ecommerce.infrastructure.adapter.out.db.entity.ProductEntity;
import ecommerce.infrastructure.adapter.out.db.repository.SpringDataProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Secondary/Driven Adapter for product database persistence.
 *
 * <p>Maps domain {@link Product} objects to {@link ProductEntity} for JPA storage.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Component
public class ProductPersistenceAdapter implements ProductRepositoryPort {
  /** The Spring Data JPA repository used for executing database operations for products. */
  private final SpringDataProductRepository repository;

  /**
   * Constructs a new ProductPersistenceAdapter with the required repository dependency.
   *
   * @param repository the Spring Data repository for performing product CRUD operations.
   */
  public ProductPersistenceAdapter(SpringDataProductRepository repository) {
    this.repository = repository;
  }

  /**
   * Persists a product domain entity to the database.
   *
   * <p>This method maps the pure domain {@link Product} object to a JPA {@link ProductEntity},
   * saves it using the underlying Spring Data repository, and maps the result back to a domain
   * object.
   *
   * @param product the domain product entity to be saved.
   * @return the saved domain product, populated with any database-generated fields.
   */
  @Override
  public Product save(Product product) {
    ProductEntity entity = this.mapToEntity(product);
    ProductEntity saved = this.repository.save(entity);
    return this.mapToDomain(saved);
  }

  /**
   * Retrieves a product domain entity based on its unique identifier.
   *
   * <p>Queries the database for a matching {@link ProductEntity} and translates it back into a pure
   * domain {@link Product}.
   *
   * @param id the unique identifier to search for.
   * @return an {@link Optional} containing the mapped domain product, or an empty Optional if not
   *     found.
   */
  @Override
  public Optional<Product> findById(Integer id) {
    return this.repository.findById(id).map(this::mapToDomain);
  }

  /**
   * Retrieves all product domain entities from the database.
   *
   * @return a list containing all stored products mapped to domain objects.
   */
  @Override
  public List<Product> findAll() {
    return this.repository.findAll().stream().map(this::mapToDomain).collect(Collectors.toList());
  }

  /**
   * Retrieves a list of product domain entities that match the provided collection of identifiers.
   *
   * @param ids a list of unique identifiers for the products to retrieve.
   * @return a list of products matching the given IDs mapped to domain objects.
   */
  @Override
  public List<Product> findAllById(List<Integer> ids) {
    return this.repository.findAllById(ids).stream()
        .map(this::mapToDomain)
        .collect(Collectors.toList());
  }

  /**
   * Removes a product from the database based on its unique identifier.
   *
   * @param id the unique identifier of the product to be deleted.
   */
  @Override
  public void deleteById(Integer id) {
    this.repository.deleteById(id);
  }

  /**
   * Converts a JPA {@link ProductEntity} into a pure domain {@link Product} object.
   *
   * @param entity the JPA entity retrieved from the database.
   * @return the corresponding domain product.
   */
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

  /**
   * Converts a pure domain {@link Product} object into a JPA {@link ProductEntity}.
   *
   * @param product the domain product containing the data to be persisted.
   * @return the corresponding JPA entity ready for database operations.
   */
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
