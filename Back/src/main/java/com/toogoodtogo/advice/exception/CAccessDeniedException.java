package com.toogoodtogo.advice.exception;

public class CAccessDeniedException extends RuntimeException {
    public CAccessDeniedException() { super(); }

    public CAccessDeniedException(String message) {
        super(message);
    }

    public CAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}