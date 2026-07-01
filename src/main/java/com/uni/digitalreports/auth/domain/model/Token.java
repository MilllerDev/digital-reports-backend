package com.uni.digitalreports.auth.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private UUID id;
    private String token;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean revoked;

    public static Token createRefreshToken(String tokenValue, UUID userId, long expiresInSeconds) {
        return Token.builder()
                .token(tokenValue)
                .userId(userId)
                .expiresAt(LocalDateTime.now().plusSeconds(expiresInSeconds))
                .revoked(false)
                .build();
    }
}
