package com.olo.authservice.domain.exception.user;


import com.olo.authservice.domain.exception.DomainException;

public class EmailRegisteredException extends DomainException {
    public EmailRegisteredException(String message) {
        super(message);
    }

    public EmailRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
