package com.uni.digitalreports.users.infrastructure.repository;

import com.uni.digitalreports.users.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryJpa extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByDniAndEnabledTrue(String dni);

    boolean existsByEmail(String email);

    boolean existsByDniAndEmail(String dni, String email);
}
