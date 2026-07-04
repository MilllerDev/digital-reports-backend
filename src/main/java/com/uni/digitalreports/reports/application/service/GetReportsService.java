package com.uni.digitalreports.reports.application.service;

import com.uni.digitalreports.reports.application.repository.ReportRepository;
import com.uni.digitalreports.reports.application.usecase.GetReportUseCase;
import com.uni.digitalreports.reports.application.usecase.GetReportsUseCase;
import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.users.domain.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetReportsService implements GetReportsUseCase, GetReportUseCase {
    private final ReportRepository repository;

    public GetReportsService(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Report> execute(UserRole role, UUID userId) {
        if (role == UserRole.ADMIN) {
            return repository.findAll();
        }
        return repository.findMine(userId);
    }

    @Override
    public Report execute(UUID id, UserRole role, UUID userId) {
        if (role == UserRole.ADMIN) {
            return repository.findById(id).orElseThrow(
                    () -> new RuntimeException("No existe este reporte")
            );
        }
        return repository.findMine(id, userId).orElseThrow(
                () -> new RuntimeException("No existe este reporte")
        );
    }
}

