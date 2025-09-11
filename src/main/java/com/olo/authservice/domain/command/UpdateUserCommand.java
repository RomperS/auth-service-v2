package com.olo.authservice.domain.command;

public record UpdateUserCommand(
        Long userId,
        String username,
        String password
) {
}
