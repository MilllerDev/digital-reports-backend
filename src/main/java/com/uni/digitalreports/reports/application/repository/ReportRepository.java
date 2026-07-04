package com.uni.digitalreports.reports.application.repository;

import com.uni.digitalreports.reports.domain.model.Report;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReportRepository {
    Report save(Report report);

    Optional<Report> findById(UUID id);

    List<Report> findMine(UUID userId);

    Optional<Report> findMine(UUID id, UUID userId);

    List<Report> findAll();
}
