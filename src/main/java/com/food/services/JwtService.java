package com.food.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final Duration ttl;
    private final String appName;

    public JwtService(@Value("${spring.application.name}") String appName,
                      @Value("${jwt.ttl}") Duration ttl,
                      JwtEncoder jwtEncoder,
                      JwtDecoder jwtDecoder) {
        this.appName = appName;
        this.ttl = ttl;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(String userId, String username, List<String> roles) {
        Instant now = Instant.now();
        Instant expiry = now.plus(ttl);

        List<String> modifiedRoles = roles.stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .collect(Collectors.toList());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(appName)
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(username)
                .claim("userId", userId)
                .claim("roles", modifiedRoles)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public boolean validateToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (JwtException e) {
            System.out.println("JWT validation failed: " + e.getMessage());
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        return (List<String>) jwtDecoder.decode(token).getClaims().get("roles");
    }

    public String extractUserId(String token) {
        Jwt jwt = jwtDecoder.decode(token);

        return jwt.getClaimAsString("userId");
    }

}