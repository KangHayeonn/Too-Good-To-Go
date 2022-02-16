package com.toogoodtogo.domain.exceptions;

public class CUploadImageInvalidException extends RuntimeException {
    public CUploadImageInvalidException() {
        super();
    }

    public CUploadImageInvalidException(String message) {
        super(message);
    }

    public CUploadImageInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}