package com.uni.digitalreports.auth.infrastructure.dto;

import com.uni.digitalreports.users.domain.model.UserRole;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        UserRole role
) {
}
