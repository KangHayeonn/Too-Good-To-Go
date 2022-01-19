package com.toogoodtogo.advice.exception;

public class CValidCheckException extends RuntimeException {
    public CValidCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CValidCheckException(String message) {
        super(message);
    }

    public CValidCheckException() {
        super();
    }
}
