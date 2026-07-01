package com.uni.digitalreports.auth.application.repository;

import com.uni.digitalreports.auth.domain.model.Token;

import java.util.UUID;

public interface TokenRepository {
    void save(Token token);

    boolean existsValid(String token);

    void revokeByUser(UUID userId);
}
