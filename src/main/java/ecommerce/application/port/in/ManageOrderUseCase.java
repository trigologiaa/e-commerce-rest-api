package ecommerce.application.port.in;

import ecommerce.domain.model.Order;
import java.util.List;

public interface ManageOrderUseCase {
  Order createOrder(Order order);

  List<Order> getAllOrders();
}
