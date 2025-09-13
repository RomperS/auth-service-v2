package com.olo.authservice.domain.exception.kafka;

import com.olo.authservice.domain.exception.DomainException;

public class InvalidMessageFormatException extends DomainException {
    public InvalidMessageFormatException(String message) {
        super(message);
    }

    public InvalidMessageFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
