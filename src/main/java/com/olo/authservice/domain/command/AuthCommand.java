package com.olo.authservice.domain.command;

public record AuthCommand(
        String username,
        String password
) {
}
