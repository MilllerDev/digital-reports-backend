package com.uni.digitalreports.reports.application.service;

import com.uni.digitalreports.reports.application.repository.ReportRepository;
import com.uni.digitalreports.reports.application.usecase.CreateReportUseCase;
import com.uni.digitalreports.reports.domain.model.Report;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateReportService implements CreateReportUseCase {
    private final ReportRepository repository;

    public CreateReportService(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Report execute(UUID userId, Report report) {
        report.setUserId(userId);
        return repository.save(report);
    }
}
