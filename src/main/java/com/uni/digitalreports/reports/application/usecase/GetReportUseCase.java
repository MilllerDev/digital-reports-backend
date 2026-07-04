package com.uni.digitalreports.reports.application.usecase;

import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.users.domain.model.UserRole;

import java.util.UUID;

public interface GetReportUseCase {
    Report execute(UUID id, UserRole role, UUID userId);
}
