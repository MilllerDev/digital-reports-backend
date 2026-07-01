package com.uni.digitalreports.auth.infrastructure.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
