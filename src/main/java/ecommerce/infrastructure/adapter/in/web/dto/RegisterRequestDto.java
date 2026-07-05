package ecommerce.infrastructure.adapter.in.web.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for user registration.
 *
 * <p>Carries the personal information and credentials required to create a new user account.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Data
public class RegisterRequestDto {
  /** The user's given first name. */
  private String firstName;

  /** The user's family or last name. */
  private String lastName;

  /** The intended email address for the new account, which will serve as the login credential. */
  private String email;

  /** The intended raw password for the new account. */
  private String password;
}

