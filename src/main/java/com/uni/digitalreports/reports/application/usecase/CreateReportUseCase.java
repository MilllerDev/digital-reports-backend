package com.uni.digitalreports.reports.application.usecase;

import com.uni.digitalreports.reports.domain.model.Report;

import java.util.UUID;

public interface CreateReportUseCase {
    Report execute(UUID userId, Report report);
}
