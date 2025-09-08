package com.olo.authservice.domain.result;

public record ValidateTokenResult(
        boolean isValid,
        String type
) {
}
