package com.olo.authservice.infrastructure.dtos.request;

public record SignupRequestDto(
        String username,
        String email,
        String password
) {
}
