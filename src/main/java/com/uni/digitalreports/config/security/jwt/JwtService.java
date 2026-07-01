package com.uni.digitalreports.config.security.jwt;

import com.uni.digitalreports.users.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final JwtProperties properties;

    public JwtService(JwtProperties properties) {
        this.properties = properties;
    }

    public String generateAccessToken(User user) {
        return generateToken(user, properties.getAccessSecret(), properties.getExpirationAccess());
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, properties.getRefreshSecret(), properties.getExpirationRefresh());
    }

    public String extractDni(String token) {
        return extractClaim(token, properties.getAccessSecret(), Claims::getSubject);
    }

    public String extractDniFromRefreshToken(String token) {
        return extractClaim(token, properties.getRefreshSecret(), Claims::getSubject);
    }

    public boolean isAccessTokenValid(String token, String email) {
        return isTokenValid(token, email, properties.getAccessSecret());
    }

    public boolean isRefreshTokenValid(String token, String email) {
        return isTokenValid(token, email, properties.getRefreshSecret());
    }

    private boolean isTokenValid(String token, String dni, String secret) {
        try {
            final String extractedDni = extractClaim(token, secret, Claims::getSubject);
            return extractedDni.equals(dni) && !isTokenExpired(token, secret);
        } catch (Exception e) {
            return false;
        }
    }

    public long getRefreshTokenExpiration() {
        return properties.getExpirationRefresh();
    }

    private String generateToken(User user, String secret, long expiration) {
        return Jwts.builder()
                .subject(user.getDni())
                .claim("name", user.getName())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(expiration)))
                .signWith(getSigningKey(secret))
                .compact();
    }

    private boolean isTokenExpired(String token, String secret) {
        return extractClaim(token, secret, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, String secret, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    private SecretKey getSigningKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
