package ecommerce.infrastructure.security;

import ecommerce.application.port.out.UserRepositoryPort;
import ecommerce.domain.model.Role;
import ecommerce.domain.model.User;
import ecommerce.infrastructure.adapter.in.web.dto.AuthRequestDto;
import ecommerce.infrastructure.adapter.in.web.dto.AuthResponseDto;
import ecommerce.infrastructure.adapter.in.web.dto.RegisterRequestDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Infrastructure service responsible for handling user authentication and registration.
 *
 * <p>Validates user credentials against the database, securely encrypts passwords using BCrypt, and
 * issues JSON Web Tokens (JWT) upon successful login.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Service
public class AuthenticationService {
  /** The outbound port for persisting and retrieving user entities. */
  private final UserRepositoryPort userRepositoryPort;

  /** The utility used to securely hash and verify passwords. */
  private final PasswordEncoder passwordEncoder;

  /** The service responsible for generating JSON Web Tokens. */
  private final JwtService jwtService;

  /** The Spring Security component that authenticates the user credentials. */
  private final AuthenticationManager authenticationManager;

  /** The Spring Security service that loads core user details from the system. */
  private final UserDetailsService userDetailsService;

  /**
   * Constructs a new AuthenticationService with the required dependencies.
   *
   * @param userRepositoryPort the port for user database operations.
   * @param passwordEncoder the encoder for securely hashing passwords.
   * @param jwtService the service for generating tokens.
   * @param authenticationManager the manager to authenticate login credentials.
   * @param userDetailsService the service to load user profiles.
   */
  public AuthenticationService(
      UserRepositoryPort userRepositoryPort,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      AuthenticationManager authenticationManager,
      UserDetailsService userDetailsService) {
    this.userRepositoryPort = userRepositoryPort;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
  }

  /**
   * Processes a new user registration.
   *
   * <p>Maps the request DTO to a domain entity, securely encodes the raw password, saves the user,
   * and generates an initial JWT so the user is immediately authenticated.
   *
   * @param request the registration details provided by the client.
   * @return a data transfer object containing the generated JWT token.
   */
  public AuthResponseDto register(RegisterRequestDto request) {
    User user =
        new User(
            null,
            request.getFirstName(),
            request.getLastName(),
            request.getEmail(),
            passwordEncoder.encode(request.getPassword()),
            Role.USER);
    userRepositoryPort.save(user);
    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
    String jwtToken = jwtService.generateToken(userDetails);
    return new AuthResponseDto(jwtToken);
  }

  /**
   * Processes a user login attempt.
   *
   * <p>Authenticates the provided credentials using the Spring Security context, retrieves the user
   * details, and issues a fresh JWT.
   *
   * @param request the login credentials (email and password) provided by the client.
   * @return a data transfer object containing the newly generated JWT token.
   */
  public AuthResponseDto login(AuthRequestDto request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
    String jwtToken = jwtService.generateToken(userDetails);
    return new AuthResponseDto(jwtToken);
  }
}

