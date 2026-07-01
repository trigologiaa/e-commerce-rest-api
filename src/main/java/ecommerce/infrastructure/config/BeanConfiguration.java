package ecommerce.infrastructure.config;

import ecommerce.application.port.out.OrderRepositoryPort;
import ecommerce.application.port.out.ProductRepositoryPort;
import ecommerce.application.usecase.OrderUseCaseImpl;
import ecommerce.application.usecase.ProductUseCaseImpl;
import ecommerce.domain.service.OrderDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
  @Bean
  public OrderDomainService orderDomainService() {
    return new OrderDomainService();
  }

  @Bean
  public ProductUseCaseImpl productUseCase(ProductRepositoryPort productRepositoryPort) {
    return new ProductUseCaseImpl(productRepositoryPort);
  }

  @Bean
  public OrderUseCaseImpl orderUseCase(
      OrderRepositoryPort orderRepositoryPort,
      ProductRepositoryPort productRepositoryPort,
      OrderDomainService orderDomainService) {
    return new OrderUseCaseImpl(orderRepositoryPort, productRepositoryPort, orderDomainService);
  }
}
