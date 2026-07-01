package ecommerce.infrastructure.adapter.in.web.controller;

import ecommerce.infrastructure.adapter.in.web.dto.AuthRequestDto;
import ecommerce.infrastructure.adapter.in.web.dto.AuthResponseDto;
import ecommerce.infrastructure.adapter.in.web.dto.RegisterRequestDto;
import ecommerce.infrastructure.security.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}