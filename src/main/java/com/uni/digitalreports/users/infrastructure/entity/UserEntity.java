package com.uni.digitalreports.users.infrastructure.entity;

import com.uni.digitalreports.users.domain.model.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_dni", nullable = false, unique = true, length = 8)
    private String dni;

    @Column(name = "user_email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "user_name", nullable = false, length = 20)
    private String name;

    @Column(name = "user_last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "encrypted_password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole role;

    @Column(name = "is_enabled", nullable = false)
    private boolean enabled;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
