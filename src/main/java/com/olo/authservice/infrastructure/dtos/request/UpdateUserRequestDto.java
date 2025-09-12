package com.olo.authservice.infrastructure.dtos.request;

public record UpdateUserRequestDto(
        String username,
        String email,
        String password
) {
}