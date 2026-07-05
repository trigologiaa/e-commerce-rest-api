package ecommerce.infrastructure.adapter.in.web.controller;

import ecommerce.application.port.in.ManageOrderUseCase;
import ecommerce.domain.model.Order;
import ecommerce.domain.model.OrderLine;
import ecommerce.infrastructure.adapter.in.web.dto.OrderRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller acting as a Primary Adapter for managing orders.
 *
 * <p>Translates incoming JSON requests into domain-friendly structures and delegates the execution
 * to the application's inbound use case port.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
  /** The inbound port (use case) for handling order-related business logic. */
  private final ManageOrderUseCase manageOrderUseCase;

  /**
   * Constructs a new OrderController with the required use case dependency.
   *
   * @param manageOrderUseCase the use case for managing orders.
   */
  public OrderController(ManageOrderUseCase manageOrderUseCase) {
    this.manageOrderUseCase = manageOrderUseCase;
  }

  /**
   * Retrieves all existing orders from the system.
   *
   * @return a {@link ResponseEntity} containing a list of all orders.
   */
  @GetMapping
  public ResponseEntity<List<Order>> getOrders() {
    return ResponseEntity.ok(this.manageOrderUseCase.getAllOrders());
  }

  /**
   * Processes a request to create a new purchase order.
   *
   * <p>This method maps the incoming DTO to a domain entity before delegating to the use case.
   *
   * @param request the data transfer object containing the order lines to be purchased.
   * @return a {@link ResponseEntity} containing the fully created order with an HTTP 201 Created
   *     status.
   */
  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto request) {
    List<OrderLine> lines =
        request.getLines().stream()
            .map(line -> new OrderLine(line.getProductId(), null, line.getQuantity(), null))
            .collect(Collectors.toList());
    Order order = new Order(null, lines, 0.0, "PENDING");
    Order createOrder = this.manageOrderUseCase.createOrder(order);
    return new ResponseEntity<>(createOrder, HttpStatus.CREATED);
  }
}
