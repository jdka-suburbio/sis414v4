package com.example.demov3.seguridad.web.dto;

public record LoginResponse(
        String token,
        String tokenType,
        long expiresIn,
        String usuario,
        String completo,
        String grupo,
        String entidad,
        String descent,
        String unidad,
        String descuni) {
}
