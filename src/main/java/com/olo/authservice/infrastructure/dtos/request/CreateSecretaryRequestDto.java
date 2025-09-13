package com.olo.authservice.infrastructure.dtos.request;

public record CreateSecretaryRequestDto(
        String username,
        String dni,
        String password
) {
}
