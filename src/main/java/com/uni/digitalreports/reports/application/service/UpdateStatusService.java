package com.uni.digitalreports.reports.application.service;

import com.uni.digitalreports.reports.application.repository.ReportRepository;
import com.uni.digitalreports.reports.application.usecase.UpdateStatusUseCase;
import com.uni.digitalreports.reports.domain.exception.ReportNotFoundException;
import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.reports.domain.model.ReportStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateStatusService implements UpdateStatusUseCase {
    private final ReportRepository repository;

    public UpdateStatusService(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Report execute(UUID id, ReportStatus status) {
        Report report = repository.findById(id).orElseThrow(
                () -> new ReportNotFoundException("No se encontró el reporte")
        );
        report.setStatus(status);
        return repository.save(report);
    }
}
