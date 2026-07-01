package com.uni.digitalreports.users.application.repository;

import com.uni.digitalreports.users.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);

    void deleteUser(UUID id);

    Optional<User> findByDni(String dni);

    boolean existsByDniEmail(String dni, String email);
}
