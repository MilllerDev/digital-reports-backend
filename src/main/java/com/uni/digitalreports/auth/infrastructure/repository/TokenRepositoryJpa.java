package com.uni.digitalreports.auth.infrastructure.repository;

import com.uni.digitalreports.auth.infrastructure.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TokenRepositoryJpa extends JpaRepository<TokenEntity, UUID> {
    List<TokenEntity> findByUserIdAndRevokedFalse(UUID userId);

    boolean existsByTokenAndRevokedFalse(String token);
}
