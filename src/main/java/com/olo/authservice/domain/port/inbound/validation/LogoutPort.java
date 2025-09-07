package com.olo.authservice.domain.port.inbound.validation;

public interface LogoutPort {
    void logout(String token);
}
