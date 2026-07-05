package ecommerce.domain.model;

/**
 * Defines the security roles available for user within the e-commerce system.
 *
 * <p>These roles are used to determine authorization levels and restrict access to specific API
 * endpoints or business logic operations (e.g., separating regular customers from system
 * administrators).
 *
 * @author trigologiaa
 * @version 1.0.0
 */
public enum Role {
  /**
   * Standard customer role.
   *
   * <p>Grants basic permissions such as browsing products, managing personal carts, and placing
   * orders.
   */
  USER,
  /**
   * Administrator role.
   *
   * <p>Grants elevated privileges such as managing product inventory, creating or deleting
   * categories, and viewing global system orders.
   */
  ADMIN
}
