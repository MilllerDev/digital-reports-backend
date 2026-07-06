package com.uni.digitalreports.reports.infrastructure.repository;

import com.uni.digitalreports.reports.infrastructure.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReportRepositoryJpa extends JpaRepository<ReportEntity, UUID> {
    List<ReportEntity> findByUserIdOrderByCreatedAtDesc(UUID userId);

    Optional<ReportEntity> findByIdAndUserId(UUID id, UUID userId);
}
