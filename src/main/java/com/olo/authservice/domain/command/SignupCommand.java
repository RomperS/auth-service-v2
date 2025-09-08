package com.olo.authservice.domain.command;

public record SignupCommand(
        Long userId,
        String username,
        String email,
        String password
) {
}
