package ecommerce.infrastructure.config;


import ecommerce.application.port.out.UserRepositoryPort;
import ecommerce.domain.model.User;
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

import java.util.Collections;

@Configuration
public class AuthConfig {
  private final UserRepositoryPort userRepositoryPort;

  public AuthConfig(UserRepositoryPort userRepositoryPort) {
    this.userRepositoryPort = userRepositoryPort;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      User user = userRepositoryPort.findByEmail(username)
          .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
      return new org.springframework.security.core.userdetails.User(user.getEmail(),
          user.getPassword(),
          Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
