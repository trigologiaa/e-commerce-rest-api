package ecommerce.domain.service;

import ecommerce.domain.exception.InsufficientStockException;
import ecommerce.domain.model.Order;
import ecommerce.domain.model.OrderLine;
import ecommerce.domain.model.Product;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderDomainService {
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
