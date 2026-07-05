package ecommerce.application.port.out;

import ecommerce.domain.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Outbound port (SPI) for product persistence.
 *
 * <p>Defines the contract that the application core requires to store, retrieve, and manage {@link
 * Product} entities.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public interface ProductRepositoryPort {
  /**
   * Persists a given product entity to the underlying storage.
   *
   * @param product the product entity to be saved.
   * @return the persisted product, potentially updated with database-generated fields (like an ID).
   */
  Product save(Product product);

  /**
   * Retrieves a product by its unique identifier.
   *
   * @param id the unique identifier of the product to search for.
   * @return an {@link Optional} containing the found product, or an empty Optional if no product
   *     matches the ID.
   */
  Optional<Product> findById(Integer id);

  /**
   * Retrieves all product entities from the underlying storage.
   *
   * @return a list containing all stored products, or an empty list if no products are found.
   */
  List<Product> findAll();

  /**
   * Retrieves a list of products that match the provided collection of unique identifiers.
   *
   * @param ids a list of unique identifiers for the products to retrieve.
   * @return a list of products matching the given IDs.
   */
  List<Product> findAllById(List<Integer> ids);

  /**
   * Removes a product from the underlying storage based on its unique identifier.
   *
   * @param id the unique identifier of the product to be deleted.
   */
  void deleteById(Integer id);
}
