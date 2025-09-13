package com.olo.authservice.domain.exception.user;

import com.olo.authservice.domain.exception.DomainException;

public class UsernameTakenException extends DomainException {
    public UsernameTakenException(String message) {
        super(message);
    }

    public UsernameTakenException(String message, Throwable cause) {
        super(message, cause);
    }
}
