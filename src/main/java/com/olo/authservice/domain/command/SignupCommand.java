package com.olo.authservice.domain.command;

public record SignupCommand(
        String username,
        String email,
        String password
) {
}
