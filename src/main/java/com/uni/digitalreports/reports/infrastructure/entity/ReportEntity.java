package com.uni.digitalreports.reports.infrastructure.entity;

import com.uni.digitalreports.reports.domain.model.Important;
import com.uni.digitalreports.reports.domain.model.ReportStatus;
import com.uni.digitalreports.users.infrastructure.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "report_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "report_asunto", nullable = false, length = 100)
    private String asunto;

    @Column(name = "report_description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_important", nullable = false)
    private Important important;

    @Column(name = "report_latitude", nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "report_longitude", nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "report_address", length = 200)
    private String address;

    @Column(name = "report_image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_status", nullable = false)
    private ReportStatus status;

    @Column(name = "is_spam", nullable = false)
    private boolean spam;

    @Column(name = "spam_reason", nullable = false)
    private String spamReason;

    @Column(name = "is_duplicate", nullable = false)
    private boolean duplicate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserEntity user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
