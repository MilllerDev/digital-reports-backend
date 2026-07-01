package com.uni.digitalreports.auth.application.service;

import com.uni.digitalreports.auth.application.repository.TokenRepository;
import com.uni.digitalreports.auth.application.usecase.LoginUseCase;
import com.uni.digitalreports.auth.domain.model.Token;
import com.uni.digitalreports.auth.infrastructure.dto.AuthResponse;
import com.uni.digitalreports.config.security.jwt.JwtService;
import com.uni.digitalreports.users.domain.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    public LoginService(JwtService jwtService, TokenRepository tokenRepository, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse execute(String dni, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dni, password)
        );
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenRepository.revokeByUser(user.getId());

        Token token = Token.createRefreshToken(refreshToken, user.getId(), jwtService.getRefreshTokenExpiration());
        tokenRepository.save(token);
        return new AuthResponse(accessToken, refreshToken);
    }
}
