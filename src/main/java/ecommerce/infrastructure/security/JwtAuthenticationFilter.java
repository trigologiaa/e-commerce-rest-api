package ecommerce.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * HTTP filter that intercepts incoming requests to validate JWTs.
 *
 * <p>Extracts the token from the "Authorization" header, verifies its cryptographic signature, and
 * populates the Spring Security Context with the authenticated user.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  /** The service handling the parsing and validation of the token. */
  private final JwtService jwtService;

  /** The service handling the retrieval of the user profile based on the token's subject. */
  private final UserDetailsService userDetailsService;

  /**
   * Constructs a new JwtAuthenticationFilter with the necessary dependencies.
   *
   * @param jwtService the token utility service.
   * @param userDetailsService the service to load user details.
   */
  public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  /**
   * Intercepts the HTTP request to process JWT authentication.
   *
   * <p>Checks for the presence of a "Bearer" token in the Authorization header. If found and valid,
   * it retrieves the corresponding user and explicitly sets the authentication in the Spring
   * Security context before allowing the request to proceed down the filter chain.
   *
   * @param request the incoming HTTP request.
   * @param response the outgoing HTTP response.
   * @param filterChain the chain of filters to pass the request/response to.
   * @throws ServletException if a servlet-specific error occurs.
   * @throws IOException if an I/O error occurs during the processing of the request.
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    jwt = authHeader.substring(7);
    userEmail = jwtService.extractUsername(jwt);
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      if (jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}
