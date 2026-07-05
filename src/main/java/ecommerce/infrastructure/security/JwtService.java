package ecommerce.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Utility service for generating, parsing, and validating JSON Web Tokens (JWT).
 *
 * <p>Uses cryptographic algorithms to securely sign tokens, ensuring that user identity and session
 * claims cannot be tampered with by external actors.
 *
 * @author trigologiaa
 * @version 1.0.0
 */
@Service
public class JwtService {
  /** The base64-encoded secret key used to cryptographically sign the tokens. */
  @Value("${spring.security.jwt.secret-key}")
  private String secretKey;

  /** The validity duration of a token in milliseconds. */
  @Value("${spring.security.jwt.expiration}")
  private long jwtExpiration;

  /**
   * Extracts the username (subject claim) from the given token.
   *
   * @param token the JWT from which to extract the username.
   * @return the extracted username.
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts a specific claim from the token using a provided resolving function.
   *
   * @param token the JWT from which to extract the claim.
   * @param claimsResolver a function defining how to extract the desired claim.
   * @param <T> the expected type of the claim.
   * @return the extracted claim.
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Generates a new JWT for the given user details without any additional claims.
   *
   * @param userDetails the user for whom the token is being generated.
   * @return the fully constructed and signed JWT string.
   */
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  /**
   * Generates a new JWT containing custom extra claims for the given user details.
   *
   * @param extraClaims a map of additional claims to include in the token payload.
   * @param userDetails the user for whom the token is being generated.
   * @return the fully constructed and signed JWT string.
   */
  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts.builder()
        .claims(extraClaims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
        .signWith(getSignInKey(), Jwts.SIG.HS256)
        .compact();
  }

  /**
   * Validates a token by ensuring it belongs to the specified user and has not expired.
   *
   * @param token the JWT to be validated.
   * @param userDetails the user to check the token against.
   * @return true if the token is valid for the user, false otherwise.
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
   * Checks whether the given token has passed its expiration date.
   *
   * @param token the JWT to check.
   * @return true if the token is expired, false otherwise.
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Retrieves the expiration date claim from the given token.
   *
   * @param token the JWT from which to extract the expiration date.
   * @return the expiration date of the token.
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Parses the given token, verifying its signature, and extracts all of its payload claims.
   *
   * @param token the JWT to parse.
   * @return the complete set of claims contained within the token.
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
  }

  /**
   * Generates the cryptographic key used for signing and verifying the JWT based on the configured
   * secret.
   *
   * @return the HMAC SHA key for cryptographic operations.
   */
  private SecretKey getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}

