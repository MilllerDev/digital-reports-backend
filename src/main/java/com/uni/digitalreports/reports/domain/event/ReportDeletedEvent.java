package com.uni.digitalreports.reports.domain.event;

import com.uni.digitalreports.users.domain.model.UserRole;

import java.util.UUID;

public record ReportDeletedEvent(UUID reportId, UUID userId, UserRole deletedBy) {
}
