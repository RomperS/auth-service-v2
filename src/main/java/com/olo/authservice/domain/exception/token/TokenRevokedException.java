package com.olo.authservice.domain.exception.token;

import com.olo.exceptions.DomainException;

public class TokenRevokedException extends DomainException {
    public TokenRevokedException(String message) {
        super(message);
    }

    public TokenRevokedException(String message, Throwable cause) {
        super(message, cause);
    }
}
