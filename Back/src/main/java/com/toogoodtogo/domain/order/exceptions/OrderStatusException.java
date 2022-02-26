package com.toogoodtogo.domain.order.exceptions;

public class OrderStatusException extends RuntimeException {

    public OrderStatusException(String message) {
        super(message);
    }
}
