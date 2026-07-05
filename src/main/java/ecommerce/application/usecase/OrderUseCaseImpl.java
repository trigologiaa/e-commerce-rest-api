package ecommerce.application.usecase;

import ecommerce.application.port.in.ManageOrderUseCase;
import ecommerce.application.port.out.OrderRepositoryPort;
import ecommerce.application.port.out.ProductRepositoryPort;
import ecommerce.domain.model.Order;
import ecommerce.domain.model.OrderLine;
import ecommerce.domain.model.Product;
import ecommerce.domain.service.OrderDomainService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link ManageOrderUseCase} inbound port.
 *
 * <p>This application service orchestrates the flow of creating an order by retrieving the
 * necessary products from the database, delegating the core business rules to the {@link
 * OrderDomainService}, and persisting the updated states.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public class OrderUseCaseImpl implements ManageOrderUseCase {
  /** The repository port handled for order persistence operations. */
  private final OrderRepositoryPort orderRepositoryPort;

  /** The repository port handled for product retrieval and updates. */
  private final ProductRepositoryPort productRepositoryPort;

  /** The domain service containing the core business rules for processing orders. */
  private final OrderDomainService orderDomainService;

  /**
   * Constructs a new OrderUseCaseImpl with the required dependencies.
   *
   * @param orderRepositoryPort the port used for order persistence operations.
   * @param productRepositoryPort the port used for product retrieval and updates.
   * @param orderDomainService the domain service containing the core business rules for orders.
   */
  public OrderUseCaseImpl(
      OrderRepositoryPort orderRepositoryPort,
      ProductRepositoryPort productRepositoryPort,
      OrderDomainService orderDomainService) {
    this.orderRepositoryPort = orderRepositoryPort;
    this.productRepositoryPort = productRepositoryPort;
    this.orderDomainService = orderDomainService;
  }

  /**
   * Processes and creates a new purchase order.
   *
   * <p>This implementation sets the initial status to "PENDING", retrieves the necessary products
   * to validate and update stock via domain service, and finally persists the changes to both the
   * products and the order.
   *
   * @param order the order entity containing the details to be created.
   * @return the fully created and persisted order.
   */
  @Override
  public Order createOrder(Order order) {
    order.setStatus("PENDING");
    List<Integer> productIds =
        order.getLines().stream().map(OrderLine::getProductId).collect(Collectors.toList());
    List<Product> products = this.productRepositoryPort.findAllById(productIds);
    this.orderDomainService.processNewOrder(order, products);
    for (Product product : products) {
      this.productRepositoryPort.save(product);
    }
    return this.orderRepositoryPort.save(order);
  }

  /**
   * Retrieves all existing purchase orders from the persistence layer.
   *
   * @return a list containing all stored orders, or an empty list if no orders are found.
   */
  @Override
  public List<Order> getAllOrders() {
    return this.orderRepositoryPort.findAll();
  }
}
