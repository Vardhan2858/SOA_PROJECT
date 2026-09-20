package com.insurance.claim_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    // =========================
    // EXTRACT USERNAME
    // =========================
    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }

    // =========================
    // EXTRACT CLAIMS
    // =========================
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // =========================
    // VALIDATE TOKEN
    // =========================
    public boolean isTokenValid(
            String token,
            String username) {

        final String extractedUsername =
                extractUsername(token);

        return extractedUsername.equals(username)
                && !isTokenExpired(token);
    }

    // =========================
    // CHECK EXPIRATION
    // =========================
    private boolean isTokenExpired(String token) {

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // =========================
    // GENERATE TOKEN
    // =========================
    public String generateToken(String username) {

        Date now = new Date();

        Date expiration =
                new Date(now.getTime() + 60 * 60 * 1000);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey(), Jwts.SIG.HS384)
                .compact();
    }
}