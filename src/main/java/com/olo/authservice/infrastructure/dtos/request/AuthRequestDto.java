package com.olo.authservice.infrastructure.dtos.request;

public record AuthRequestDto(
        String username,
        String password
) {
}
