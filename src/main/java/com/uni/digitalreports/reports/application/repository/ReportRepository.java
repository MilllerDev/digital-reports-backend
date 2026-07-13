package com.uni.digitalreports.reports.application.repository;

import com.uni.digitalreports.reports.domain.model.Report;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReportRepository {
    Report save(Report report);

    Optional<Report> findById(UUID id);

    Optional<Report> findMine(UUID id, UUID userId);

    boolean exists(UUID id);

    List<Report> findMine(UUID userId);

    List<Report> findAll();

    List<Report> findAllToday();

    List<Report> findNearby(BigDecimal latitude, BigDecimal longitude, double radiusKm);

    void delete(UUID id);
}
