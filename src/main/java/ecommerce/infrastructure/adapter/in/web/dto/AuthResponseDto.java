package ecommerce.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for authentication responses.
 *
 * <p>Returns the generated authentication token back to the client upon successful login.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
public class AuthResponseDto {
  /** The securely generated token (e.g., JWT) to be used for subsequent authorized requests. */
  private String token;
}

