package ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the E-commerce API application.
 *
 * <p>This class triggers the Spring Boot auto-configuration, scans for components within the
 * package, and launches the embedded server.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@SpringBootApplication
public class Application {
  /**
   * The main method that bootstraps and launches the Spring application context.
   *
   * @param args Command-line arguments passed during application startup.
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
