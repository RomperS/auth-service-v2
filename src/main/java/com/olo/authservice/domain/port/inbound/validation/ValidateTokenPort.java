package com.olo.authservice.domain.port.inbound.validation;

public interface ValidateTokenPort {
    String validateToken(String token);
}
