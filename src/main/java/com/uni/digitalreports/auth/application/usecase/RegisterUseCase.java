package com.uni.digitalreports.auth.application.usecase;

import com.uni.digitalreports.auth.infrastructure.dto.AuthResponse;
import com.uni.digitalreports.users.domain.model.User;

public interface RegisterUseCase {
    AuthResponse execute(User user);
}
