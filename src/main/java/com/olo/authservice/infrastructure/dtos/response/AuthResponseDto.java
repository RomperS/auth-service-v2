package com.olo.authservice.infrastructure.dtos.response;

import java.time.Instant;

public record AuthResponseDto(
        String refreshToken,
        Instant expireAt,
        AccessTokenResponseDto accessToken
) {
}