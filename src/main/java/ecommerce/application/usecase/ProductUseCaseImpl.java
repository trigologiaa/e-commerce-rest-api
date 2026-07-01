package ecommerce.application.usecase;

import ecommerce.application.port.in.ManageProductUseCase;
import ecommerce.application.port.out.ProductRepositoryPort;
import ecommerce.domain.exception.ResourceNotFoundException;
import ecommerce.domain.model.Product;
import java.util.List;

public class ProductUseCaseImpl implements ManageProductUseCase {
  private final ProductRepositoryPort productRepositoryPort;

  public ProductUseCaseImpl(ProductRepositoryPort productRepositoryPort) {
    this.productRepositoryPort = productRepositoryPort;
  }

  @Override
  public Product createProduct(Product product) {
    return this.productRepositoryPort.save(product);
  }

  @Override
  public Product getProductById(Integer id) {
    return this.productRepositoryPort
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
  }

  @Override
  public List<Product> getAllProducts() {
    return this.productRepositoryPort.findAll();
  }

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

  @Override
  public void deleteProduct(Integer id) {
    this.getProductById(id);
    this.productRepositoryPort.deleteById(id);
  }
}
