package com.toogoodtogo.advice.exception;

public class CNoLoginException extends RuntimeException{
    public CNoLoginException() {
        super();
    }

    public CNoLoginException(String message) {
        super(message);
    }

    public CNoLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
