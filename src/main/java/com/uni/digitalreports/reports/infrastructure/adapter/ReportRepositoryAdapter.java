package com.uni.digitalreports.reports.infrastructure.adapter;

import com.uni.digitalreports.reports.application.repository.ReportRepository;
import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.reports.infrastructure.ReportMapper;
import com.uni.digitalreports.reports.infrastructure.repository.ReportRepositoryJpa;
import org.springframework.stereotype.Repository;

@Repository
public class ReportRepositoryAdapter implements ReportRepository {
    private final ReportRepositoryJpa repositoryJpa;
    private final ReportMapper mapper;

    public ReportRepositoryAdapter(ReportRepositoryJpa repositoryJpa, ReportMapper mapper) {
        this.repositoryJpa = repositoryJpa;
        this.mapper = mapper;
    }

    @Override
    public Report save(Report report) {
        var entity = mapper.toEntity(report);
        return mapper.toModel(repositoryJpa.save(entity));
    }
}
