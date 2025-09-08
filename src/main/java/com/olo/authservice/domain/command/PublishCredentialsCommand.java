package com.olo.authservice.domain.command;

public record PublishCredentialsCommand(
        String userId,
        String role
) {
}