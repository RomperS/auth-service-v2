package com.olo.authservice.domain.result;

import java.time.Instant;

public record AuthResult(
        String refreshToken,
        Instant expireAt,
        AccessTokenResult accessToken
) {
}