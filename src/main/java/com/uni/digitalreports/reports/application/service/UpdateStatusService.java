package com.uni.digitalreports.reports.application.service;

import com.uni.digitalreports.reports.application.repository.ReportRepository;
import com.uni.digitalreports.reports.application.usecase.UpdateStatusUseCase;
import com.uni.digitalreports.reports.domain.event.ReportUpdatedEvent;
import com.uni.digitalreports.reports.domain.exception.ReportNotFoundException;
import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.reports.domain.model.ReportStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateStatusService implements UpdateStatusUseCase {
    private final ReportRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public UpdateStatusService(ReportRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Report execute(UUID id, ReportStatus status) {
        Report report = repository.findById(id).orElseThrow(
                () -> new ReportNotFoundException("No se encontró el reporte")
        );
        report.setStatus(status);
        Report saved = repository.save(report);
        eventPublisher.publishEvent(new ReportUpdatedEvent(saved));
        return saved;
    }
}
