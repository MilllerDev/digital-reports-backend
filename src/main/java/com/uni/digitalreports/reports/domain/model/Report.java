package com.uni.digitalreports.reports.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private UUID id;
    private String asunto;
    private String description;
    private Important important;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
    private String imageUrl;
    private ReportStatus status;
    private boolean spam;
    private String spamReason;
    private boolean duplicate;
    private UUID userId;
}
