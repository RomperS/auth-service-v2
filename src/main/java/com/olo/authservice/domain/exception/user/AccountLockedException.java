package com.olo.authservice.domain.exception.user;

import com.olo.exceptions.DomainException;

public class AccountLockedException extends DomainException {
    public AccountLockedException(String message) {
        super(message);
    }

    public AccountLockedException(String message, Throwable cause) {
        super(message, cause);
    }
}
