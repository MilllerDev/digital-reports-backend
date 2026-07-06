package com.uni.digitalreports.reports.application.usecase;

import com.uni.digitalreports.users.domain.model.UserRole;

import java.util.UUID;

public interface DeleteReportUseCase {
    void execute(UserRole role, UUID userId, UUID id);
}
