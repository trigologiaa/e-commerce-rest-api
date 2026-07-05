package ecommerce.application.usecase;

import ecommerce.application.port.in.ManageProductUseCase;
import ecommerce.application.port.out.ProductRepositoryPort;
import ecommerce.domain.exception.ResourceNotFoundException;
import ecommerce.domain.model.Product;
import java.util.List;

/**
 * Implementation of the {@link ManageProductUseCase} inbound port.
 *
 * <p>Handles the logic required to pass product data between the web interface and the database
 * repositories, ensuring domain entities exist before updates or deletions.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public class ProductUseCaseImpl implements ManageProductUseCase {
  /** The repository port handled for product persistence and retrieval operations. */
  private final ProductRepositoryPort productRepositoryPort;

  /**
   * Constructs a new ProductUseCaseImpl with the required product repository dependency.
   *
   * @param productRepositoryPort the port used for product persistence operations.
   */
  public ProductUseCaseImpl(ProductRepositoryPort productRepositoryPort) {
    this.productRepositoryPort = productRepositoryPort;
  }

  /**
   * Processes and creates a new product by delegating the save operation to the repository.
   *
   * @param product the product entity containing the details to be saved.
   * @return the fully created and persisted product.
   */
  @Override
  public Product createProduct(Product product) {
    return this.productRepositoryPort.save(product);
  }

  /**
   * Retrieves a specific product by its unique identifier.
   *
   * @param id the unique identifier of the product to retrieve.
   * @return the requested product.
   * @throws ResourceNotFoundException if no product is found matching the provided id.
   */
  @Override
  public Product getProductById(Integer id) {
    return this.productRepositoryPort
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
  }

  /**
   * Retrieves all existing products from the persistence layer.
   *
   * @return a list containing all stored products, or an empty list if no products are found.
   */
  @Override
  public List<Product> getAllProducts() {
    return this.productRepositoryPort.findAll();
  }

  /**
   * Updates the details of an existing product.
   *
   * <p>This implementation first verifies that the product exists, updates its modifiable fields
   * with the provided data, and then persists the changes back to the repository.
   *
   * @param id the unique identifier of the product to be updated.
   * @param updatedProduct the product entity containing the new information.
   * @return the fully updated and persisted product.
   * @throws ResourceNotFoundException if the product to update does not exist in the system.
   */
  @Override
  public Product updateProduct(Integer id, Product updatedProduct) {
    Product existing = this.getProductById(id);
    existing.setName(updatedProduct.getName());
    existing.setDescription(updatedProduct.getDescription());
    existing.setPrice(updatedProduct.getPrice());
    existing.setStock(updatedProduct.getStock());
    existing.setImageUrl(updatedProduct.getImageUrl());
    return productRepositoryPort.save(existing);
  }

  /**
   * Deletes a specific product from the system based on its unique identifier.
   *
   * <p>This implementation checks for the product's existence before attempting to remove it from
   * the underlying storage.
   *
   * @param id the unique identifier of the product to be removed.
   * @throws ResourceNotFoundException if the product to delete does not exist.
   */
  @Override
  public void deleteProduct(Integer id) {
    this.getProductById(id);
    this.productRepositoryPort.deleteById(id);
  }
}
