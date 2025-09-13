package com.olo.authservice.domain.exception.user;

import com.olo.authservice.domain.exception.DomainException;

public class MalformedRecordException extends DomainException {
    public MalformedRecordException(String message) {
        super(message);
    }

    public MalformedRecordException(String message, Throwable cause) {
        super(message, cause);
    }
}
