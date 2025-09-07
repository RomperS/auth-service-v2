package com.olo.authservice.domain.result;

import java.time.Instant;

public record AccessTokenResult(
        String token,
        Instant expireAt
) {
}
