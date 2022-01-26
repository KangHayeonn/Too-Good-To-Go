package com.toogoodtogo.domain.order.exceptions;

public class OrderCancelException extends RuntimeException {

    public OrderCancelException(String message) {
        super(message);
    }
}
