package com.olo.authservice.domain.exception.user;

import com.olo.authservice.domain.exception.DomainException;

public class AdminUserBlockNotAllowedException extends DomainException {
    public AdminUserBlockNotAllowedException(String message) {
        super(message);
    }

    public AdminUserBlockNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
