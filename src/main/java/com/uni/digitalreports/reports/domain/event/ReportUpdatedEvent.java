package com.uni.digitalreports.reports.domain.event;

import com.uni.digitalreports.reports.domain.model.Report;

public record ReportUpdatedEvent(Report report) {
}
