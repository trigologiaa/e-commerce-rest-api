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

@Service
public class AuthenticationService {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthenticationService(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder,
                                 JwtService jwtService, AuthenticationManager authenticationManager,
                                 UserDetailsService userDetailsService) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public AuthResponseDto register(RegisterRequestDto request) {
        User user = new User(
                null,
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()), // Hash the password
                Role.USER
        );
        userRepositoryPort.save(user);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);
        
        return new AuthResponseDto(jwtToken);
    }

    public AuthResponseDto login(AuthRequestDto request) {
        // This line validates the email and password automatically
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // If it gets here, the credentials are valid
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);
        
        return new AuthResponseDto(jwtToken);
    }
}