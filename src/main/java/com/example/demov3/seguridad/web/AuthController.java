package com.example.demov3.seguridad.web;

import com.example.demov3.seguridad.web.dto.LoginRequest;
import com.example.demov3.seguridad.web.dto.LoginResponse;
import com.example.demov3.seguridad.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Login contra PASSW (clave con cifrado legacy). Devuelve JWT")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
