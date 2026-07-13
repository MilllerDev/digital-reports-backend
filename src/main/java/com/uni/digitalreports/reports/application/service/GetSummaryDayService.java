package com.uni.digitalreports.reports.application.service;

import com.uni.digitalreports.ai.reports.dto.DailySummaryResult;
import com.uni.digitalreports.ai.reports.port.AiAnalysisPort;
import com.uni.digitalreports.reports.application.repository.ReportRepository;
import com.uni.digitalreports.reports.application.usecase.GetSummaryDayUseCase;
import com.uni.digitalreports.reports.domain.model.Report;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSummaryDayService implements GetSummaryDayUseCase {
    private final ReportRepository repository;
    private final AiAnalysisPort aiAnalysisPort;

    public GetSummaryDayService(ReportRepository repository, AiAnalysisPort aiAnalysisPort) {
        this.repository = repository;
        this.aiAnalysisPort = aiAnalysisPort;
    }

    @Override
    public DailySummaryResult execute() {
        List<Report> reports = repository.findAllToday();
        return aiAnalysisPort.makeSummary(reports);
    }
}
