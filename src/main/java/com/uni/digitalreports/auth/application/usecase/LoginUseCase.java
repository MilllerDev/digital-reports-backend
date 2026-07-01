package com.uni.digitalreports.auth.application.usecase;

import com.uni.digitalreports.auth.infrastructure.dto.AuthResponse;

public interface LoginUseCase {
    AuthResponse execute(String dni, String password);
}
