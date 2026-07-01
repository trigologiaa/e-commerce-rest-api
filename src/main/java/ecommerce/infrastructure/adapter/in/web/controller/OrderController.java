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

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final ManageOrderUseCase manageOrderUseCase;

  public OrderController(ManageOrderUseCase manageOrderUseCase) {
    this.manageOrderUseCase = manageOrderUseCase;
  }

  @GetMapping
  public ResponseEntity<List<Order>> getOrders() {
    return ResponseEntity.ok(this.manageOrderUseCase.getAllOrders());
  }

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
