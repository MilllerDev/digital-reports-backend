package com.uni.digitalreports.reports.infrastructure;

import com.uni.digitalreports.reports.domain.model.Important;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ReportRequestDto(
        @NotBlank(message = "El asunto es obligatorio")
        @Size(max = 20, message = "El asunto no puede superar los 20 caracteres")
        String asunto,

        @NotBlank(message = "La descripción es obligatoria")
        String description,

        @NotNull(message = "El nivel de importancia es obligatorio")
        Important important,

        @NotNull(message = "La latitud es obligatoria")
        @DecimalMin(value = "-90.0", message = "La latitud debe ser mayor o igual a -90")
        @DecimalMax(value = "90.0", message = "La latitud debe ser menor o igual a 90")
        BigDecimal latitude,

        @NotNull(message = "La longitud es obligatoria")
        @DecimalMin(value = "-180.0", message = "La longitud debe ser mayor o igual a -180")
        @DecimalMax(value = "180.0", message = "La longitud debe ser menor o igual a 180")
        BigDecimal longitude,

        @Size(max = 200, message = "La dirección no puede superar los 200 caracteres")
        String address,

        String imageUrl
) {
}
