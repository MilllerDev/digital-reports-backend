package com.uni.digitalreports.reports.application.service;

import com.uni.digitalreports.reports.application.repository.ReportRepository;
import com.uni.digitalreports.reports.application.usecase.DeleteReportUseCase;
import com.uni.digitalreports.reports.domain.event.ReportDeletedEvent;
import com.uni.digitalreports.reports.domain.exception.ReportAccessException;
import com.uni.digitalreports.reports.domain.exception.ReportNotFoundException;
import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.users.domain.model.UserRole;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteReportService implements DeleteReportUseCase {
    private final ReportRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public DeleteReportService(ReportRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void execute(UserRole role, UUID userId, UUID id) {
        Report report = repository.findById(id).orElseThrow(
                () -> new ReportNotFoundException("No se encontró el reporte")
        );
        if (role != UserRole.ADMIN && !report.getUserId().equals(userId)) {
            throw new ReportAccessException("No tienes acceso a eliminar este reporte");
        }
        repository.delete(id);
        eventPublisher.publishEvent(new ReportDeletedEvent(id, report.getUserId(), role));
    }
}
