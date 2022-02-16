package com.toogoodtogo.domain.user.exceptions;

public class CUserNotFoundException extends RuntimeException {
    public CUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CUserNotFoundException(String message) {
        super(message);
    }

    public CUserNotFoundException() {
        super();
    }
}