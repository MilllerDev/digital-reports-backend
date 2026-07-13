package com.uni.digitalreports.reports.domain.event;

import com.uni.digitalreports.reports.domain.model.Report;

public record ReportCreatedEvent(Report report) {
}
