package ecommerce.application.port.out;

import ecommerce.domain.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
  Product save(Product product);

  Optional<Product> findById(Integer id);

  List<Product> findAll();

  List<Product> findAllById(List<Integer> ids);

  void deleteById(Integer id);
}
