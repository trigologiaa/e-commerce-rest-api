package ecommerce.application.port.in;

import ecommerce.domain.model.Product;
import java.util.List;

public interface ManageProductUseCase {
  Product createProduct(Product product);

  Product getProductById(Integer id);

  List<Product> getAllProducts();

  Product updateProduct(Integer id, Product product);

  void deleteProduct(Integer id);
}
