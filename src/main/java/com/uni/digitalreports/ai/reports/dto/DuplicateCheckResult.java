package com.uni.digitalreports.ai.reports.dto;

public record DuplicateCheckResult(
        boolean isDuplicate,
        String matchedReportId,
        double similarity
) {
}
