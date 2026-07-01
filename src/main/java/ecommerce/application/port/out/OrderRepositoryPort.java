package ecommerce.application.port.out;

import ecommerce.domain.model.Order;
import java.util.List;

public interface OrderRepositoryPort {
  Order save(Order order);

  List<Order> findAll();
}
