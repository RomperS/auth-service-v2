package com.olo.authservice.domain.port.inbound.tokens;

public interface RevokeTokenPort {
    void revokeToken(String jti);
}
