package com.uni.digitalreports.config;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        T data,
        String message,
        boolean success,
        Object error,
        LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<T>(data, message, true, null, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(String message, Object error) {
        return new ApiResponse<T>(null, message, false, error, LocalDateTime.now());
    }
}
