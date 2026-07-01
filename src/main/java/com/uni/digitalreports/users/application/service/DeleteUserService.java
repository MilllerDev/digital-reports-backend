package com.uni.digitalreports.users.application.service;

import com.uni.digitalreports.users.application.uscase.DeleteUserUseCase;
import com.uni.digitalreports.users.application.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteUserService implements DeleteUserUseCase {
    private final UserRepository repository;

    public DeleteUserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(UUID id) {
        repository.deleteUser(id);
    }
}
