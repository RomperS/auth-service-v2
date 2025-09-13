package com.olo.authservice.domain.command;

public record CreateSecretaryCommand(
        String username,
        Long dni,
        String password
) {
}
