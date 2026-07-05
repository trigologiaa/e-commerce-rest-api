package ecommerce.infrastructure.config;

import ecommerce.application.port.out.OrderRepositoryPort;
import ecommerce.application.port.out.ProductRepositoryPort;
import ecommerce.application.usecase.OrderUseCaseImpl;
import ecommerce.application.usecase.ProductUseCaseImpl;
import ecommerce.domain.service.OrderDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Central configuration class for Dependency Injection via Spring.
 *
 * <p>This class wires the Hexagonal Architecture together by instantiating pure domain services and
 * use cases, injecting the necessary infrastructure-driven adapters.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Configuration
public class BeanConfiguration {
  /**
   * Creates and registers the core domain service for orders.
   *
   * @return a new instance of the {@link OrderDomainService}.
   */
  @Bean
  public OrderDomainService orderDomainService() {
    return new OrderDomainService();
  }

  /**
   * Creates and registers the product use case implementation.
   *
   * @param productRepositoryPort the injected outbound port for product persistence.
   * @return the configured {@link ProductUseCaseImpl}.
   */
  @Bean
  public ProductUseCaseImpl productUseCase(ProductRepositoryPort productRepositoryPort) {
    return new ProductUseCaseImpl(productRepositoryPort);
  }

  /**
   * Creates and registers the order use case implementation.
   *
   * @param orderRepositoryPort the injected outbound port for order persistence.
   * @param productRepositoryPort the injected outbound port for product retrieval and updates.
   * @param orderDomainService the injected core domain service for order logic.
   * @return the configured {@link OrderUseCaseImpl}.
   */
  @Bean
  public OrderUseCaseImpl orderUseCase(
      OrderRepositoryPort orderRepositoryPort,
      ProductRepositoryPort productRepositoryPort,
      OrderDomainService orderDomainService) {
    return new OrderUseCaseImpl(orderRepositoryPort, productRepositoryPort, orderDomainService);
  }
}
