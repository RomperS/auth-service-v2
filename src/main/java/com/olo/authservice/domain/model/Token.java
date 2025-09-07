package com.olo.authservice.domain.model;

import java.time.Instant;

public record Token(
        Long id,
        String jti,
        Long userId,
        String refreshToken,
        boolean isRevoked,
        Instant expiredAt
) {
}