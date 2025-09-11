package com.olo.authservice.domain.exception.kafka;

import com.olo.exceptions.DomainException;

public class InvalidMessageFormatException extends DomainException {
    public InvalidMessageFormatException(String message) {
        super(message);
    }

    public InvalidMessageFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
