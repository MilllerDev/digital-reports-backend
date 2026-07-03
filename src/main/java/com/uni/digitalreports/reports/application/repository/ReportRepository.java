package com.uni.digitalreports.reports.application.repository;

import com.uni.digitalreports.reports.domain.model.Report;

public interface ReportRepository {
    Report save(Report report);
}
