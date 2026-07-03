package com.uni.digitalreports.auth.application.service;

import com.uni.digitalreports.auth.application.repository.TokenRepository;
import com.uni.digitalreports.auth.application.usecase.RegisterUseCase;
import com.uni.digitalreports.auth.domain.model.Token;
import com.uni.digitalreports.auth.infrastructure.dto.AuthResponse;
import com.uni.digitalreports.config.security.jwt.JwtService;
import com.uni.digitalreports.users.application.repository.UserRepository;
import com.uni.digitalreports.users.domain.exception.UserAlreadyExists;
import com.uni.digitalreports.users.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService implements RegisterUseCase {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public AuthResponse execute(User user) {
        if (repository.existsByDniEmail(user.getDni(), user.getEmail())) {
            throw new UserAlreadyExists("El email o dni está en uso,intente iniciar sesión");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        User created = repository.save(user);
        String accessToken = jwtService.generateAccessToken(created);
        String refreshToken = jwtService.generateRefreshToken(created);

        Token token = Token.createRefreshToken(refreshToken, created.getId(), jwtService.getRefreshTokenExpiration());
        tokenRepository.save(token);
        return new AuthResponse(accessToken, refreshToken, created.getRole());
    }
}
