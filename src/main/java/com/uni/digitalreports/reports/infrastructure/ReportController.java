package com.uni.digitalreports.reports.infrastructure;

import com.uni.digitalreports.config.ApiResponse;
import com.uni.digitalreports.reports.application.usecase.CreateReportUseCase;
import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.users.domain.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final CreateReportUseCase createReport;
    private final ReportMapper mapper;

    public ReportController(CreateReportUseCase createReport, ReportMapper mapper) {
        this.createReport = createReport;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReportResponseDto>> create(
            @AuthenticationPrincipal User user, @Valid @RequestBody ReportRequestDto dto
    ) {
        Report response = createReport.execute(user.getId(), mapper.toModel(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Reporte realizado", mapper.toDto(response))
        );
    }
}
