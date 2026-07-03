package com.uni.digitalreports.auth.application.service;

import com.uni.digitalreports.auth.application.repository.TokenRepository;
import com.uni.digitalreports.auth.application.usecase.RefreshTokenUseCase;
import com.uni.digitalreports.auth.domain.exception.InvalidTokenException;
import com.uni.digitalreports.auth.domain.exception.TokenRevokedException;
import com.uni.digitalreports.auth.domain.model.Token;
import com.uni.digitalreports.auth.infrastructure.dto.AuthResponse;
import com.uni.digitalreports.config.security.jwt.JwtService;
import com.uni.digitalreports.users.application.repository.UserRepository;
import com.uni.digitalreports.users.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService implements RefreshTokenUseCase {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public RefreshTokenService(TokenRepository tokenRepository, UserRepository userRepository, JwtService jwtService) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse execute(String token) {
        if (!tokenRepository.existsValid(token)) {
            throw new TokenRevokedException("No existe el token o está revocado");
        }
        String dni = jwtService.extractDniFromRefreshToken(token);
        if (!jwtService.isRefreshTokenValid(token, dni)) {
            throw new InvalidTokenException("Token de refresco inválido o expirado");
        }
        User user = userRepository.findByDni(dni).orElseThrow(
                () -> new InvalidTokenException("Usuario no encontrado")
        );
        tokenRepository.revokeByUser(user.getId());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Token newToken = Token.createRefreshToken(refreshToken, user.getId(), jwtService.getRefreshTokenExpiration());
        tokenRepository.save(newToken);
        return new AuthResponse(accessToken, refreshToken, user.getRole());
    }
}
