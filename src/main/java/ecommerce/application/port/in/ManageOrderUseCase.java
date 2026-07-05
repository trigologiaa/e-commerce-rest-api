package ecommerce.application.port.in;

import ecommerce.domain.model.Order;
import java.util.List;

/**
 * Inbound port (Use Case) for managing order operations.
 *
 * <p>Defines the contract for orchestrating order creation and retrieval from the presentation/web
 * layer into the core application.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public interface ManageOrderUseCase {
  /**
   * Processes and creates a new purchase order in the system.
   *
   * @param order the order entity containing the details to be created.
   * @return the fully created order, typically populated with a generated identifier.
   */
  Order createOrder(Order order);

  /**
   * Retrieves all existing purchase orders from the system.
   *
   * @return a list containing all orders, or an empty list if no orders are found.
   */
  List<Order> getAllOrders();
}
