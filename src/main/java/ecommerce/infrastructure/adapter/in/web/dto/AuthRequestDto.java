package ecommerce.infrastructure.adapter.in.web.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for authentication requests.
 *
 * <p>Carries the user's login credentials from the client to the authentication endpoint.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
public class AuthRequestDto {
  /** The email address used as the login credential. */
  private String email;

  /** The plain-text password provided by the user for authentication. */
  private String password;
}

