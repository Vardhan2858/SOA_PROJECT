package com.insurance.claim_service.controller;

import com.insurance.claim_service.security.JwtService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        // Simple username/password for project demonstration
        if ("admin".equals(request.username())
                && "admin123".equals(request.password())) {

            String token =
                    jwtService.generateToken(request.username());

            return new LoginResponse(token);
        }

        throw new RuntimeException(
                "Invalid username or password"
        );
    }

    public record LoginRequest(
            String username,
            String password
    ) {
    }

    public record LoginResponse(
            String token
    ) {
    }
}