package ecommerce.infrastructure.adapter.in.web.controller;

import ecommerce.infrastructure.adapter.in.web.dto.AuthRequestDto;
import ecommerce.infrastructure.adapter.in.web.dto.AuthResponseDto;
import ecommerce.infrastructure.adapter.in.web.dto.RegisterRequestDto;
import ecommerce.infrastructure.security.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller acting as a Primary Adapter for authentication operations.
 *
 * <p>Exposes endpoints for user registration and login, returning JWT tokens upon success.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  /** The security service responsible for handling user registration and login logic. */
  private final AuthenticationService authenticationService;

  /**
   * Constructs a new AuthController with the required authentication service.
   *
   * @param authenticationService the service handling security operations.
   */
  public AuthController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  /**
   * Processes a user registration request.
   *
   * @param request the data transfer object containing the new user's details.
   * @return a {@link ResponseEntity} containing the authentication token upon successful
   *     registration.
   */
  @PostMapping("/register")
  public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto request) {
    return ResponseEntity.ok(authenticationService.register(request));
  }

  /**
   * Processes a user login request.
   *
   * @param request the data transfer object containing the user's credentials.
   * @return a {@link ResponseEntity} containing the authentication token upon successful login.
   */
  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
    return ResponseEntity.ok(authenticationService.login(request));
  }
}

