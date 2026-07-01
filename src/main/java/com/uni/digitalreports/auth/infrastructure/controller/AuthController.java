package com.uni.digitalreports.auth.infrastructure.controller;

import com.uni.digitalreports.auth.application.usecase.LoginUseCase;
import com.uni.digitalreports.auth.application.usecase.RefreshTokenUseCase;
import com.uni.digitalreports.auth.application.usecase.RegisterUseCase;
import com.uni.digitalreports.auth.infrastructure.dto.AuthResponse;
import com.uni.digitalreports.auth.infrastructure.dto.LoginRequestDto;
import com.uni.digitalreports.auth.infrastructure.dto.RefreshRequestDto;
import com.uni.digitalreports.auth.infrastructure.dto.RegisterRequestDto;
import com.uni.digitalreports.config.ApiResponse;
import com.uni.digitalreports.users.domain.model.User;
import com.uni.digitalreports.users.infrastructure.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LoginUseCase login;
    private final RegisterUseCase register;
    private final RefreshTokenUseCase refreshToken;
    private final UserMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequestDto dto) {
        AuthResponse response = login.execute(dto.dni(), dto.password());
        return ResponseEntity.ok(ApiResponse.success("Login correcto", response));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequestDto dto) {
        User user = mapper.toModel(dto);
        AuthResponse response = register.execute(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Registro exitoso", response)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@Valid @RequestBody RefreshRequestDto dto) {
        AuthResponse response = refreshToken.execute(dto.refreshToken());
        return ResponseEntity.ok(ApiResponse.success("Token refrescado correctamente", response));
    }
}
