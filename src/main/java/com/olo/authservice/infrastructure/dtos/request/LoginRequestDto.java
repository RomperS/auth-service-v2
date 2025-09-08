package com.olo.authservice.infrastructure.dtos.request;

public record LoginRequestDto(
        String username,
        String password
) {
}
