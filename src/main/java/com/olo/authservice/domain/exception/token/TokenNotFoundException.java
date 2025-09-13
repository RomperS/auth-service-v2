package com.olo.authservice.domain.exception.token;

import com.olo.authservice.domain.exception.DomainException;

public class TokenNotFoundException extends DomainException {
    public TokenNotFoundException(String message) {
        super(message);
    }

    public TokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
