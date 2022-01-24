package com.toogoodtogo.domain.order;

public enum OrderStatus {
    ORDER_COMPLETED,
    CANCELED,
    PREPARING,
    WAITING_PICKUP,
    PICKUP_COMPLETED;

    public Boolean canCancel() {
        return true;
    }
}
