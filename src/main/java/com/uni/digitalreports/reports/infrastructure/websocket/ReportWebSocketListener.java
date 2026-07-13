package com.uni.digitalreports.reports.infrastructure.websocket;

import com.uni.digitalreports.reports.domain.event.ReportCreatedEvent;
import com.uni.digitalreports.reports.domain.event.ReportDeletedEvent;
import com.uni.digitalreports.reports.domain.event.ReportSocketMessage;
import com.uni.digitalreports.reports.domain.event.ReportUpdatedEvent;
import com.uni.digitalreports.reports.domain.model.Report;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReportWebSocketListener {
    private final SimpMessagingTemplate messagingTemplate;

    public ReportWebSocketListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleReportCreated(ReportCreatedEvent event) {
        Report report = event.report();
        ReportSocketMessage payload = new ReportSocketMessage("CREATED", report, null, null);
        messagingTemplate.convertAndSend("/topic/admin/reports", payload);
        messagingTemplate.convertAndSend("/topic/user/" + report.getUserId() + "/reports", payload);
    }

    @EventListener
    public void handleReportDeleted(ReportDeletedEvent event) {
        ReportSocketMessage payload = new ReportSocketMessage("DELETED", null, event.reportId(), event.userId());
        messagingTemplate.convertAndSend("/topic/admin/reports", payload);
        messagingTemplate.convertAndSend("/topic/user/" + event.userId() + "/reports", payload);
    }

    @EventListener
    public void handleReportUpdated(ReportUpdatedEvent event) {
        Report report = event.report();
        ReportSocketMessage payload = new ReportSocketMessage("UPDATED", report, null, null);
        messagingTemplate.convertAndSend("/topic/admin/reports", payload);
        messagingTemplate.convertAndSend("/topic/user/" + report.getUserId() + "/reports", payload);
    }
}
