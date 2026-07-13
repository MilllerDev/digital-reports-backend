package com.uni.digitalreports.reports.infrastructure;

import com.uni.digitalreports.reports.domain.model.Important;
import com.uni.digitalreports.reports.domain.model.ReportStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReportResponseDto(
        UUID id,
        String asunto,
        String description,
        Important important,
        BigDecimal latitude,
        BigDecimal longitude,
        String address,
        String imageUrl,
        ReportStatus status,
        boolean spam,
        String spamReason,
        boolean duplicate,
        LocalDateTime createdAt,
        UUID userId
) {
}
