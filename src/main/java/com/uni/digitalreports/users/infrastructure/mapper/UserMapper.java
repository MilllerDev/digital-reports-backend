package com.uni.digitalreports.users.infrastructure.mapper;

import com.uni.digitalreports.auth.infrastructure.dto.RegisterRequestDto;
import com.uni.digitalreports.users.domain.model.User;
import com.uni.digitalreports.users.domain.model.UserRole;
import com.uni.digitalreports.users.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {UserRole.class})
public interface UserMapper {
    @Mapping(target = "authorities", ignore = true)
    User toModel(UserEntity entity);


    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "role", expression = "java(UserRole.USER)")
    @Mapping(target = "authorities", ignore = true)
    User toModel(RegisterRequestDto dto);

    UserEntity toEntity(User user);
}
