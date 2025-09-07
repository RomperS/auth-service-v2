package com.olo.authservice.domain.port.inbound.tokens;

public interface GenerateAccessTokenPort {
    String generateAccessToken(String token);
}
