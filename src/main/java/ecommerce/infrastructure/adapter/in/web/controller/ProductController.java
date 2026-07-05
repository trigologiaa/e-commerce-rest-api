package ecommerce.infrastructure.adapter.in.web.controller;

import ecommerce.application.port.in.ManageProductUseCase;
import ecommerce.domain.model.Product;
import ecommerce.infrastructure.adapter.in.web.dto.ProductDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller acting as a Primary Adapter for managing products.
 *
 * <p>Exposes endpoints for CRUD operations on products, translating incoming DTOs into domain
 * entities and delegating to the application's inbound use case port.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
  /** The inbound port (use case) for handling product-related business logic. */
  private final ManageProductUseCase manageProductUseCase;

  /**
   * Constructs a new ProductController with the required use case dependency.
   *
   * @param manageProductUseCase the use case for managing products.
   */
  public ProductController(ManageProductUseCase manageProductUseCase) {
    this.manageProductUseCase = manageProductUseCase;
  }

  /**
   * Retrieves all existing products from the system.
   *
   * @return a {@link ResponseEntity} containing a list of all products.
   */
  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    return ResponseEntity.ok(this.manageProductUseCase.getAllProducts());
  }

  /**
   * Retrieves a specific product by its unique identifier.
   *
   * @param id the unique identifier of the product.
   * @return a {@link ResponseEntity} containing the requested product.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
    return ResponseEntity.ok(this.manageProductUseCase.getProductById(id));
  }

  /**
   * Processes a request to create a new product.
   *
   * @param dto the validated data transfer object containing the new product's details.
   * @return a {@link ResponseEntity} containing the created product with an HTTP 201 Created
   *     status.
   */
  @PostMapping
  public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDto dto) {
    Product product =
        new Product(
            null,
            dto.getName(),
            dto.getDescription(),
            dto.getPrice(),
            dto.getStock(),
            dto.getImageUrl(),
            null);
    return new ResponseEntity<>(
        this.manageProductUseCase.createProduct(product), HttpStatus.CREATED);
  }

  /**
   * Processes a request to update an existing product.
   *
   * @param id the unique identifier of the product to be updated.
   * @param dto the validated data transfer object containing the updated details.
   * @return a {@link ResponseEntity} containing the fully updated product.
   */
  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(
      @PathVariable Integer id, @Valid @RequestBody ProductDto dto) {
    Product product =
        new Product(
            id,
            dto.getName(),
            dto.getDescription(),
            dto.getPrice(),
            dto.getStock(),
            dto.getImageUrl(),
            null);
    return ResponseEntity.ok(this.manageProductUseCase.updateProduct(id, product));
  }

  /**
   * Processes a request to delete a specific product.
   *
   * @param id the unique identifier of the product to be removed.
   * @return a {@link ResponseEntity} with no content (HTTP 204) upon successful deletion.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
    this.manageProductUseCase.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }
}
