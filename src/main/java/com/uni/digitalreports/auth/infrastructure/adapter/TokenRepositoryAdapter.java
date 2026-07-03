package com.uni.digitalreports.auth.infrastructure.adapter;

import com.uni.digitalreports.auth.application.repository.TokenRepository;
import com.uni.digitalreports.auth.domain.model.Token;
import com.uni.digitalreports.auth.infrastructure.dto.TokenMapper;
import com.uni.digitalreports.auth.infrastructure.entity.TokenEntity;
import com.uni.digitalreports.auth.infrastructure.repository.TokenRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TokenRepositoryAdapter implements TokenRepository {
    private final TokenRepositoryJpa repositoryJpa;
    private final TokenMapper mapper;

    public TokenRepositoryAdapter(TokenRepositoryJpa repositoryJpa, TokenMapper mapper) {
        this.repositoryJpa = repositoryJpa;
        this.mapper = mapper;
    }

    @Override
    public void save(Token token) {
        TokenEntity entity = mapper.toEntity(token);
        repositoryJpa.save(entity);
    }

    @Override
    public boolean existsValid(String token) {
        return repositoryJpa.existsByTokenAndRevokedFalse(token);
    }

    @Override
    public void revokeByUser(UUID userId) {
        List<TokenEntity> entities = repositoryJpa.findByUserIdAndRevokedFalse(userId);
        if (!entities.isEmpty()) {
            entities.forEach(token -> token.setRevoked(true));
        }
        repositoryJpa.saveAll(entities);
    }
}
