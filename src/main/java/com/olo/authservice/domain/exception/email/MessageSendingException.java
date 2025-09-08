package com.olo.authservice.domain.exception.email;

public class MessageSendingException extends EmailException {
    public MessageSendingException(String message) {
        super(message);
    }

    public MessageSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
