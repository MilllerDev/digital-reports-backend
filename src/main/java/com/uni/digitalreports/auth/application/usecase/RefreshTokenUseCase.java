package com.uni.digitalreports.auth.application.usecase;

import com.uni.digitalreports.auth.infrastructure.dto.AuthResponse;

public interface RefreshTokenUseCase {
    AuthResponse execute(String token);
}
