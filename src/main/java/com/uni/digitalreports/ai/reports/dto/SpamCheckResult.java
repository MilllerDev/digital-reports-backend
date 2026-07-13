package com.uni.digitalreports.ai.reports.dto;

public record SpamCheckResult(
        boolean isSpam,
        double confidence,
        String reason
) {
}
