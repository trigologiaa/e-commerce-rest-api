package ecommerce.application.port.out;

import ecommerce.domain.model.Order;
import java.util.List;

/**
 * Outbound port (SPI) for order persistence.
 *
 * <p>Defines the contract that the application core requires to store and retrieve {@link Order}
 * entities. Implemented by Driven Adapters in the infrastructure layer.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public interface OrderRepositoryPort {
  /**
   * Persists a given order entity to the underlying storage.
   *
   * @param order the order entity to be saved.
   * @return the persisted order, potentially updated with database-generated fields (like an ID).
   */
  Order save(Order order);

  /**
   * Retrieves all order entities from the underlying storage.
   *
   * @return a list containing all stored orders, or an empty list if no orders are found.
   */
  List<Order> findAll();
}
