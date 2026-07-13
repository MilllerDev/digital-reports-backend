package com.uni.digitalreports.ai.reports.port;

import com.uni.digitalreports.ai.reports.dto.DailySummaryResult;
import com.uni.digitalreports.ai.reports.dto.DuplicateCheckResult;
import com.uni.digitalreports.ai.reports.dto.SpamCheckResult;
import com.uni.digitalreports.reports.domain.model.Report;

import java.util.List;

public interface AiAnalysisPort {
    SpamCheckResult checkSpam(String asunto, String description);

    DuplicateCheckResult checkDuplicate(String description, double latitude, double longitude, List<Report> reports);

    DailySummaryResult makeSummary(List<Report> reports);
}
