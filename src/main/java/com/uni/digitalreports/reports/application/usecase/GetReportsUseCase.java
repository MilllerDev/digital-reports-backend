package com.uni.digitalreports.reports.application.usecase;

import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.users.domain.model.UserRole;

import java.util.List;
import java.util.UUID;

public interface GetReportsUseCase {
    List<Report> execute(UserRole role, UUID userId);
}
