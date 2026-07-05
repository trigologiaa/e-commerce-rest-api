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

/**
 * Secondary/Driven Adapter for order database persistence.
 *
 * <p>Maps domain {@link Order} objects to {@link OrderEntity} for JPA storage, handling the
 * conversion of the entire aggregate including its associated order lines.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Component
public class OrderPersistenceAdapter implements OrderRepositoryPort {
  /** The Spring Data JPA repository used for executing database operations for orders. */
  private final SpringDataOrderRepository repository;

  /**
   * Constructs a new OrderPersistenceAdapter with the required repository dependency.
   *
   * @param repository the Spring Data repository for performing order CRUD operations.
   */
  public OrderPersistenceAdapter(SpringDataOrderRepository repository) {
    this.repository = repository;
  }

  /**
   * Persists an order domain entity to the database.
   *
   * <p>This method maps the pure domain {@link Order} object to a JPA {@link OrderEntity}, saves it
   * using the underlying Spring Data repository, and maps the result back to a domain object.
   *
   * @param order the domain order entity to be saved.
   * @return the saved domain order, populated with any database-generated fields.
   */
  @Override
  public Order save(Order order) {
    OrderEntity entity = this.mapToEntity(order);
    OrderEntity saved = this.repository.save(entity);
    return this.mapToDomain(saved);
  }

  /**
   * Retrieves all order domain entities from the database.
   *
   * @return a list containing all stored orders mapped to domain objects.
   */
  @Override
  public List<Order> findAll() {
    return this.repository.findAll().stream().map(this::mapToDomain).collect(Collectors.toList());
  }

  /**
   * Converts a JPA {@link OrderEntity} and its associated lines into a pure domain {@link Order}
   * object.
   *
   * @param entity the JPA entity retrieved from the database.
   * @return the corresponding domain order.
   */
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

  /**
   * Converts a pure domain {@link Order} object and its lines into a JPA {@link OrderEntity}.
   *
   * @param order the domain order containing the data to be persisted.
   * @return the corresponding JPA entity ready for database operations.
   */
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
