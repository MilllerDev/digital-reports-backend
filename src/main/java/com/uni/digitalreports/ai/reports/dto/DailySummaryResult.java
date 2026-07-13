package com.uni.digitalreports.ai.reports.dto;

import java.util.List;

public record DailySummaryResult(
        int totalReports,
        String summary,
        int pendingCount,
        int criticalCount,
        List<String> mainCategories,
        List<String> hotspots,
        List<String> recommendations
) {
}
