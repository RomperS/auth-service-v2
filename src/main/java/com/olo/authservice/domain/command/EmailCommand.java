package com.olo.authservice.domain.command;

public record EmailCommand(
        String to,
        String subject,
        String body
) {
}
