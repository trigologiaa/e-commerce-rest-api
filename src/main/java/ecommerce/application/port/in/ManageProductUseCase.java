package ecommerce.application.port.in;

import ecommerce.domain.model.Product;
import java.util.List;

/**
 * Inbound port (Use Case) for managing product operations.
 *
 * <p>Orchestrates CRUD (Create, Read, Update, Delete) operations for products, acting as a mediator
 * between the web controllers and the persistence layer.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public interface ManageProductUseCase {
  /**
   * Processes and saves a new product in the system.
   *
   * @param product the product entity containing the details to be created.
   * @return the fully created product, typically populated with a generated identifier.
   */
  Product createProduct(Product product);

  /**
   * Retrieves a specific product by its unique identifier.
   *
   * @param id the unique identifier of the product to retrieve.
   * @return the requested product.
   */
  Product getProductById(Integer id);

  /**
   * Retrieves all existing products from the system.
   *
   * @return a list containing all products, or an empty list if no products are found.
   */
  List<Product> getAllProducts();

  /**
   * Updates the details of an existing product.
   *
   * @param id the unique identifier of the product to be updated.
   * @param product the product entity containing the updated information.
   * @return the fully updated product.
   */
  Product updateProduct(Integer id, Product product);

  /**
   * Deletes a specific product from the system based on its unique identifier.
   *
   * @param id the unique identifier of the product to be removed.
   */
  void deleteProduct(Integer id);
}
