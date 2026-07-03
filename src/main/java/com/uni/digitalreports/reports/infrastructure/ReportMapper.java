package com.uni.digitalreports.reports.infrastructure;

import com.uni.digitalreports.reports.domain.model.Report;
import com.uni.digitalreports.reports.infrastructure.entity.ReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "id", ignore = true)
    Report toModel(ReportRequestDto dto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    ReportEntity toEntity(Report report);

    @Mapping(target = "userId", source = "user.id")
    Report toModel(ReportEntity entity);

    ReportResponseDto toDto(Report report);
}
