package com.toogoodtogo.domain.security.exceptions;

public class CAccessDeniedException extends RuntimeException {
    public CAccessDeniedException() { super(); }

    public CAccessDeniedException(String message) {
        super(message);
    }

    public CAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}