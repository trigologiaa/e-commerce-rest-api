package ecommerce.infrastructure.config;

import ecommerce.application.port.out.UserRepositoryPort;
import ecommerce.domain.model.User;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for Spring Security authentication components.
 *
 * <p>Defines the beans required for fetching user details, encoding passwords, and managing the
 * overall authentication process.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Configuration
public class AuthConfig {
  /** The outbound port used to query user data from the database. */
  private final UserRepositoryPort userRepositoryPort;

  /**
   * Constructs a new AuthConfig with the required user repository dependency.
   *
   * @param userRepositoryPort the port for accessing user persistence.
   */
  public AuthConfig(UserRepositoryPort userRepositoryPort) {
    this.userRepositoryPort = userRepositoryPort;
  }

  /**
   * Provides the custom UserDetailsService bean.
   *
   * <p>Instructs Spring Security on how to retrieve a user by their email address and maps their
   * domain role to a Spring Security GrantedAuthority.
   *
   * @return the configured user details service.
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      User user =
          this.userRepositoryPort
              .findByEmail(username)
              .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
      return new org.springframework.security.core.userdetails.User(
          user.getEmail(),
          user.getPassword(),
          Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
    };
  }

  /**
   * Provides the password encoder bean.
   *
   * <p>Configures BCrypt as the hashing algorithm for securely storing and verifying passwords.
   *
   * @return the BCrypt password encoder.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Provides the Data Access Object (DAO) authentication provider bean.
   *
   * <p>Wires together the custom UserDetailsService and the BCrypt password encoder to authenticate
   * users against the database.
   *
   * @return the fully configured DAO authentication provider.
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider =
        new DaoAuthenticationProvider(this.userDetailsService());
    authProvider.setPasswordEncoder(this.passwordEncoder());
    return authProvider;
  }

  /**
   * Exposes the overarching AuthenticationManager bean.
   *
   * @param config the Spring Security authentication configuration.
   * @return the authentication manager used to process login requests.
   * @throws Exception if an error occurs while retrieving the manager.
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
