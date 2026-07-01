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

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ManageProductUseCase manageProductUseCase;

  public ProductController(ManageProductUseCase manageProductUseCase) {
    this.manageProductUseCase = manageProductUseCase;
  }

  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    return ResponseEntity.ok(this.manageProductUseCase.getAllProducts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
    return ResponseEntity.ok(this.manageProductUseCase.getProductById(id));
  }

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

  @PutMapping
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

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
    this.manageProductUseCase.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }
}
