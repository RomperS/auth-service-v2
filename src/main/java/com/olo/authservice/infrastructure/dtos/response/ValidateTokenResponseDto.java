package com.olo.authservice.infrastructure.dtos.response;

public record ValidateTokenResponseDto(
        boolean isValid,
        String type
) {
}
