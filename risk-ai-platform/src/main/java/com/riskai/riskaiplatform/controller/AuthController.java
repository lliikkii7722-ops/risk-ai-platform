package com.riskai.riskaiplatform.controller;

import com.riskai.riskaiplatform.dto.AuthResponse;
import com.riskai.riskaiplatform.dto.LoginRequest;
import com.riskai.riskaiplatform.dto.RegisterRequest;
import com.riskai.riskaiplatform.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}