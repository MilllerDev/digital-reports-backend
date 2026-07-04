package com.uni.digitalreports.reports.infrastructure;

import com.uni.digitalreports.config.ApiResponse;
import com.uni.digitalreports.reports.application.usecase.CreateReportUseCase;
import com.uni.digitalreports.reports.application.usecase.GetReportUseCase;
import com.uni.digitalreports.reports.application.usecase.GetReportsUseCase;
import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.users.domain.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final CreateReportUseCase createReport;
    private final GetReportUseCase getReport;
    private final GetReportsUseCase getReports;
    private final ReportMapper mapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReportResponseDto>>> findAll(@AuthenticationPrincipal User user) {
        List<Report> reports = getReports.execute(user.getRole(), user.getId());
        List<ReportResponseDto> response = reports.stream().map(mapper::toDto).toList();
        return ResponseEntity.ok(
                ApiResponse.success("Todos los reportes", response)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReportResponseDto>> findById(
            @AuthenticationPrincipal User user, @PathVariable UUID id
    ) {
        ReportResponseDto response = mapper.toDto(getReport.execute(id, user.getRole(), user.getId()));
        return ResponseEntity.ok(
                ApiResponse.success("Reporte encontrado", response)
        );
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
