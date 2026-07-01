package com.uni.digitalreports.auth.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequestDto(
        @NotBlank String refreshToken
) {
}
