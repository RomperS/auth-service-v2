package com.olo.authservice.domain.port.inbound.tokens;

public interface GenerateActivationTokenPort {
    String generateActivationToken(Long userId);
}
