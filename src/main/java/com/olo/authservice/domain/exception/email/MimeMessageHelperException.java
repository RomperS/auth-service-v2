package com.olo.authservice.domain.exception.email;

public class MimeMessageHelperException extends EmailException {
    public MimeMessageHelperException(String message) {
        super(message);
    }

    public MimeMessageHelperException(String message, Throwable cause) {
        super(message, cause);
    }
}
