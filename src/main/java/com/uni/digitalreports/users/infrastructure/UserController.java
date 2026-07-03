package com.uni.digitalreports.users.infrastructure;

import com.uni.digitalreports.config.ApiResponse;
import com.uni.digitalreports.users.application.uscase.DeleteUserUseCase;
import com.uni.digitalreports.users.domain.model.User;
import com.uni.digitalreports.users.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final DeleteUserUseCase deleteUser;
    private final UserMapper mapper;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDto>> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success("Sessión actual", mapper.toDto(user)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable UUID id) {
        deleteUser.execute(id);
    }
}
