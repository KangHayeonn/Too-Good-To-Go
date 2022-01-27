package com.toogoodtogo.domain.security.exceptions;

public class CPasswordLoginFailedException  extends RuntimeException {
    public CPasswordLoginFailedException() { super(); }

    public CPasswordLoginFailedException(String message) {
        super(message);
    }

    public CPasswordLoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}