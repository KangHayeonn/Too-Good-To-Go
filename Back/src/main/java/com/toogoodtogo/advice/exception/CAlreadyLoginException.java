package com.toogoodtogo.advice.exception;

public class CAlreadyLoginException extends RuntimeException {
    public CAlreadyLoginException() {
        super();
    }

    public CAlreadyLoginException(String message) {
        super(message);
    }

    public CAlreadyLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
