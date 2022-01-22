package com.toogoodtogo.advice.exception;

public class CProductNotFoundException extends RuntimeException {
    public CProductNotFoundException() {
        super();
    }

    public CProductNotFoundException(String message) {
        super(message);
    }

    public CProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
