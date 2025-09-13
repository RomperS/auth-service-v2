package com.olo.authservice.domain.exception.token;

import com.olo.authservice.domain.exception.DomainException;

public class MissingTokenException extends DomainException {
    public MissingTokenException(String message) {
        super(message);
    }

    public MissingTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
