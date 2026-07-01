package ecommerce.infrastructure.adapter.out.db;

import ecommerce.application.port.out.OrderRepositoryPort;
import ecommerce.domain.model.Order;
import ecommerce.domain.model.OrderLine;
import ecommerce.infrastructure.adapter.out.db.entity.OrderEntity;
import ecommerce.infrastructure.adapter.out.db.entity.OrderLineEntity;
import ecommerce.infrastructure.adapter.out.db.repository.SpringDataOrderRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceAdapter implements OrderRepositoryPort {
  private final SpringDataOrderRepository repository;

  public OrderPersistenceAdapter(SpringDataOrderRepository repository) {
    this.repository = repository;
  }

  @Override
  public Order save(Order order) {
    OrderEntity entity = this.mapToEntity(order);
    OrderEntity saved = this.repository.save(entity);
    return this.mapToDomain(saved);
  }

  @Override
  public List<Order> findAll() {
    return this.repository.findAll().stream().map(this::mapToDomain).collect(Collectors.toList());
  }

  private Order mapToDomain(OrderEntity entity) {
    List<OrderLine> lines =
        entity.getLines().stream()
            .map(
                line ->
                    new OrderLine(
                        line.getProductId(),
                        line.getProductName(),
                        line.getQuantity(),
                        line.getPrice()))
            .collect(Collectors.toList());
    return new Order(entity.getId(), lines, entity.getTotalAmount(), entity.getStatus());
  }

  private OrderEntity mapToEntity(Order order) {
    OrderEntity entity = new OrderEntity();
    entity.setId(order.getId());
    entity.setStatus(order.getStatus());
    entity.setTotalAmount(order.getTotalAmount());
    List<OrderLineEntity> lineEntities =
        order.getLines().stream()
            .map(
                lineEntity -> {
                  OrderLineEntity orderLineEntity = new OrderLineEntity();
                  orderLineEntity.setProductId(lineEntity.getProductId());
                  orderLineEntity.setProductName(lineEntity.getProductName());
                  orderLineEntity.setPrice(lineEntity.getPrice());
                  orderLineEntity.setQuantity(lineEntity.getQuantity());
                  return orderLineEntity;
                })
            .collect(Collectors.toList());
    entity.setLines(lineEntities);
    return entity;
  }
}
