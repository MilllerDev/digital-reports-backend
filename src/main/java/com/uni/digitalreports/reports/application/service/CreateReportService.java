package com.uni.digitalreports.reports.application.service;

import com.uni.digitalreports.ai.reports.dto.DuplicateCheckResult;
import com.uni.digitalreports.ai.reports.dto.SpamCheckResult;
import com.uni.digitalreports.ai.reports.port.AiAnalysisPort;
import com.uni.digitalreports.reports.application.repository.ReportRepository;
import com.uni.digitalreports.reports.application.usecase.CreateReportUseCase;
import com.uni.digitalreports.reports.domain.event.ReportCreatedEvent;
import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.reports.domain.model.ReportStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreateReportService implements CreateReportUseCase {
    private static final double DUPLICATE_RADIUS_KM = 0.1;

    private final ReportRepository repository;
    private final AiAnalysisPort aiAnalysisPort;
    private final ApplicationEventPublisher eventPublisher;

    public CreateReportService(ReportRepository repository, AiAnalysisPort aiAnalysisPort, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.aiAnalysisPort = aiAnalysisPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Report execute(UUID userId, Report report) {
        report.setUserId(userId);

        SpamCheckResult checkSpam = aiAnalysisPort.checkSpam(report.getAsunto(), report.getDescription());
        report.setSpam(checkSpam.isSpam());
        report.setSpamReason(checkSpam.reason());
        report.setStatus(checkSpam.isSpam() ? ReportStatus.RECHAZADO : ReportStatus.PENDIENTE);

        List<Report> nearbyReports = repository.findNearby(
                report.getLatitude(), report.getLongitude(), DUPLICATE_RADIUS_KM);

        if (!nearbyReports.isEmpty()) {
            DuplicateCheckResult checkDuplicate = aiAnalysisPort.checkDuplicate(
                    report.getDescription(),
                    report.getLatitude().doubleValue(),
                    report.getLongitude().doubleValue(),
                    nearbyReports);
            report.setDuplicate(checkDuplicate.isDuplicate());
        }
        Report saved = repository.save(report);
        eventPublisher.publishEvent(new ReportCreatedEvent(saved));
        return saved;
    }
}
