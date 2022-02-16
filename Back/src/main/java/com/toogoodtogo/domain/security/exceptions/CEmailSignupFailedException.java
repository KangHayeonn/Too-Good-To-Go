package com.toogoodtogo.domain.security.exceptions;

// 이미 가입된 이메일로 가입
public class CEmailSignupFailedException extends RuntimeException{
    public CEmailSignupFailedException() {
        super();
    }

    public CEmailSignupFailedException(String message) {
        super(message);
    }

    public CEmailSignupFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}