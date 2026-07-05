package ecommerce.infrastructure.config;

import ecommerce.infrastructure.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Main security configuration class for the application.
 *
 * <p>Configures the HTTP security filter chain, defining which endpoints are public, which require
 * authentication, and sets up a stateless session policy for JWT usage.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  /** The custom filter responsible for intercepting and validating JWTs. */
  private final JwtAuthenticationFilter jwtAuthFilter;

  /** The authentication provider that verifies user credentials. */
  private final AuthenticationProvider authenticationProvider;

  /**
   * Constructs a new SecurityConfiguration with the required filter and provider.
   *
   * @param jwtAuthFilter the JWT validation filter.
   * @param authenticationProvider the configured authentication provider.
   */
  public SecurityConfiguration(
      JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.authenticationProvider = authenticationProvider;
  }

  /**
   * Configures the main Spring Security filter chain.
   *
   * <p>This method disables CSRF (as tokens are used), defines endpoint authorization rules
   * (permitting open access to auth and product read endpoints), mandates a stateless session, and
   * inserts the custom JWT filter into the request pipeline.
   *
   * @param http the {@link HttpSecurity} to modify.
   * @return the fully built security filter chain.
   * @throws Exception if an error occurs during configuration.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/api/auth/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/products/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
