package com.uni.digitalreports.reports.application.usecase;

import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.reports.domain.model.ReportStatus;

import java.util.UUID;

public interface UpdateStatusUseCase {
    Report execute(UUID id, ReportStatus status);
}
