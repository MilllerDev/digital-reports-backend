package com.uni.digitalreports.reports.application.service;

import com.uni.digitalreports.reports.application.repository.ReportRepository;
import com.uni.digitalreports.reports.application.usecase.DeleteReportUseCase;
import com.uni.digitalreports.reports.domain.exception.ReportAccessException;
import com.uni.digitalreports.reports.domain.exception.ReportNotFoundException;
import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.users.domain.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteReportService implements DeleteReportUseCase {
    private final ReportRepository repository;

    public DeleteReportService(ReportRepository repository) {
        this.repository = repository;
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
    }
}
