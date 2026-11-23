package com.example.demov3.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class JwtService {

    private final JwtEncoder encoder;
    private final Duration expiration;

    public JwtService(JwtEncoder encoder, @Value("${app.jwt.expiration-minutes}") long expirationMinutes) {
        this.encoder = encoder;
        this.expiration = Duration.ofMinutes(expirationMinutes);
    }

    public long getExpirationSeconds() {
        return expiration.toSeconds();
    }

    public String issue(String usuario, String grupo, List<String> roles, String entidad, String unidad) {
        Instant now = Instant.now();
        JwtClaimsSet.Builder claims = JwtClaimsSet.builder()
                .issuer("demov3")
                .subject(usuario)
                .issuedAt(now)
                .expiresAt(now.plus(expiration))
                .claim("roles", roles)
                .claim("grupo", grupo);
        if (entidad != null) {
            claims.claim("entidad", entidad);
        }
        if (unidad != null) {
            claims.claim("unidad", unidad);
        }
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return encoder.encode(JwtEncoderParameters.from(header, claims.build())).getTokenValue();
    }
}
