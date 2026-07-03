package com.uni.digitalreports.auth.infrastructure;

import com.uni.digitalreports.auth.domain.model.Token;
import com.uni.digitalreports.auth.infrastructure.entity.TokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    @Mapping(target = "user.id", source = "userId")
    TokenEntity toEntity(Token token);

    @Mapping(target = "userId", source = "user.id")
    Token toModel(TokenEntity entity);
}
