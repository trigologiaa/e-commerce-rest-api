package ecommerce.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Spring Web MVC settings.
 *
 * <p>Handles the setup of global Cross-Origin Resource Sharing (CORS) rules to allow external
 * clients (like front-end applications) to communicate with this API.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
  /**
   * Configures CORS mappings for the application.
   *
   * <p>Currently configured to allow requests from any origin using standard HTTP methods.
   *
   * @param registry the CORS registry to append mapping rules to.
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("*");
  }
}
