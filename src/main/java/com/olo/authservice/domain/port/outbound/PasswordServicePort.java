package com.olo.authservice.domain.port.outbound;

public interface PasswordServicePort {
    String encode(String password);
    boolean matches(String rawPassword, String encodedPassword);
}
