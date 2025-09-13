package com.olo.authservice.domain.exception.user;

import com.olo.authservice.domain.exception.DomainException;

public class SuperAdminCreationDeniedException extends DomainException {
    public SuperAdminCreationDeniedException(String message) {
        super(message);
    }

    public SuperAdminCreationDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
