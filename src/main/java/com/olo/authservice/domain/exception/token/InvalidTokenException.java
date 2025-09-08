package com.olo.authservice.domain.exception.token;

import com.olo.exceptions.DomainException;

public class InvalidTokenException extends DomainException {
    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
