package com.uni.digitalreports.reports.infrastructure.adapter;

import com.uni.digitalreports.reports.application.repository.ReportRepository;
import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.reports.infrastructure.ReportMapper;
import com.uni.digitalreports.reports.infrastructure.repository.ReportRepositoryJpa;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<Report> findById(UUID id) {
        return repositoryJpa.findById(id).map(mapper::toModel);
    }

    @Override
    public List<Report> findMine(UUID userId) {
        return repositoryJpa.findByUserIdOrderByCreatedAtDesc(userId).stream().map(
                mapper::toModel
        ).toList();
    }

    @Override
    public Optional<Report> findMine(UUID id, UUID userId) {
        return repositoryJpa.findByIdAndUserId(id, userId).map(mapper::toModel);
    }

    @Override
    public boolean exists(UUID id) {
        return repositoryJpa.existsById(id);
    }

    @Override
    public List<Report> findAll() {
        Sort sort = Sort.by("createdAt").descending();
        return repositoryJpa.findAll(sort).stream().map(mapper::toModel).toList();
    }

    @Override
    public void delete(UUID id) {
        repositoryJpa.deleteById(id);
    }
}
