package com.uni.digitalreports.reports.infrastructure.repository;

import com.uni.digitalreports.reports.infrastructure.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReportRepositoryJpa extends JpaRepository<ReportEntity, UUID> {
    List<ReportEntity> findByUserIdOrderByCreatedAtDesc(UUID userId);

    Optional<ReportEntity> findByIdAndUserId(UUID id, UUID userId);

    List<ReportEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT r FROM ReportEntity r
            WHERE (6371 * acos(
                cos(radians(:lat)) * cos(radians(r.latitude)) *
                cos(radians(r.longitude) - radians(:lng)) +
                sin(radians(:lat)) * sin(radians(r.latitude))
            )) <= :radius
            """)
    List<ReportEntity> findNearby(@Param("lat") BigDecimal latitude,
                                  @Param("lng") BigDecimal longitude,
                                  @Param("radius") double radiusKm);
}
