package com.toogoodtogo.advice.exception;

public class CShopNotFoundException extends RuntimeException {
    public CShopNotFoundException() {
        super();
    }

    public CShopNotFoundException(String message) {
        super(message);
    }

    public CShopNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
