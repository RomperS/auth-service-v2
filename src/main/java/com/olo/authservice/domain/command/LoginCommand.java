package com.olo.authservice.domain.command;

public record LoginCommand(
        String username,
        String password
) {
}
