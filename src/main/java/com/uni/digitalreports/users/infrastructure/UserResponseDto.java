package com.uni.digitalreports.users.infrastructure;

import com.uni.digitalreports.users.domain.model.UserRole;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String dni,
        UserRole role,
        boolean enabled
) {
}
