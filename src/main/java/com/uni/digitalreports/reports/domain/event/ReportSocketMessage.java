package com.uni.digitalreports.reports.domain.event;

import com.uni.digitalreports.reports.domain.model.Report;

import java.util.UUID;

public record ReportSocketMessage(
        String type, Report report, UUID reportId, UUID userId
) {
}
