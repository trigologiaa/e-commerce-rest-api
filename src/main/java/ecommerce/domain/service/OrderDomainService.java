package ecommerce.domain.service;

import ecommerce.domain.exception.InsufficientStockException;
import ecommerce.domain.model.Order;
import ecommerce.domain.model.OrderLine;
import ecommerce.domain.model.Product;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Domain service responsible for executing core business logic related to orders.
 *
 * <p>This service operates independently of any infrastructure or framework. It validates business
 * invariants, such as ensuring sufficient stock for all requested products before confirming an
 * order, and applies the corresponding state changes to the domain entities.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public class OrderDomainService {
  /**
   * Processes a newly created order by validating stock, deducting quantities, setting historical
   * prices, and calculating the final total.
   *
   * @param order the order containing the requested lines.
   * @param dbProducts the list of actual products fetched from the database.
   * @throws InsufficientStockException if a product is not found or lacks sufficient stock.
   */
  public void processNewOrder(Order order, List<Product> dbProducts) {
    Map<Integer, Product> productMap =
        dbProducts.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
    for (OrderLine line : order.getLines()) {
      Product product = productMap.get(line.getProductId());
      if (product == null) {
        throw new InsufficientStockException(
            "Product with ID " + line.getProductId() + " not found.");
      }
      if (product.getStock() < line.getQuantity()) {
        throw new InsufficientStockException(
            "Insufficient stock for product: " + product.getName());
      }
      product.decreaseStock(line.getQuantity());
      line.setProductName(product.getName());
      line.setPrice(product.getPrice());
    }
    order.calculateTotal();
    order.setStatus("CONFIRMED");
  }
}
