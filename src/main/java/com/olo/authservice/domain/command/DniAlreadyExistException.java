package com.olo.authservice.domain.command;

import com.olo.authservice.domain.exception.DomainException;

public class DniAlreadyExistException extends DomainException {
    public DniAlreadyExistException(String message) {
        super(message);
    }

    public DniAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
