package com.uni.digitalreports.auth.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank String dni,
        @NotBlank String password
) {
}
