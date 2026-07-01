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

public class OrderUseCaseImpl implements ManageOrderUseCase {
  private final OrderRepositoryPort orderRepositoryPort;
  private final ProductRepositoryPort productRepositoryPort;
  private final OrderDomainService orderDomainService;

  public OrderUseCaseImpl(
      OrderRepositoryPort orderRepositoryPort,
      ProductRepositoryPort productRepositoryPort,
      OrderDomainService orderDomainService) {
    this.orderRepositoryPort = orderRepositoryPort;
    this.productRepositoryPort = productRepositoryPort;
    this.orderDomainService = orderDomainService;
  }

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

  @Override
  public List<Order> getAllOrders() {
    return this.orderRepositoryPort.findAll();
  }
}
