package com.olo.authservice.infrastructure.dtos.response;

import java.time.Instant;

public record AccessTokenResponseDto(
        String token,
        Instant expireAt
) {
}
