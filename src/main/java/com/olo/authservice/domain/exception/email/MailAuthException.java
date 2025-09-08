package com.olo.authservice.domain.exception.email;

public class MailAuthException extends EmailException {
    public MailAuthException(String message) {
        super(message);
    }

    public MailAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
