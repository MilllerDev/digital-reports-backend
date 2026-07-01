package com.uni.digitalreports.users.infrastructure.adapter;

import com.uni.digitalreports.users.application.repository.UserRepository;
import com.uni.digitalreports.users.domain.model.User;
import com.uni.digitalreports.users.infrastructure.entity.UserEntity;
import com.uni.digitalreports.users.infrastructure.mapper.UserMapper;
import com.uni.digitalreports.users.infrastructure.repository.UserRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryAdapter implements UserRepository {
    private final UserRepositoryJpa repositoryJpa;
    private final UserMapper mapper;

    public UserRepositoryAdapter(UserRepositoryJpa repositoryJpa, UserMapper mapper) {
        this.repositoryJpa = repositoryJpa;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        return mapper.toModel(repositoryJpa.save(entity));
    }

    @Override
    public void deleteUser(UUID id) {
        repositoryJpa.deleteById(id);
    }

    @Override
    public Optional<User> findByDni(String dni) {
        return repositoryJpa.findByDniAndEnabledTrue(dni).map(mapper::toModel);
    }

    @Override
    public boolean existsByDniEmail(String dni, String email) {
        return repositoryJpa.existsByDniAndEmail(dni, email);
    }
}
