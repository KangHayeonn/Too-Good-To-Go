package com.toogoodtogo.domain.order;

public enum OrderStatus {
    WAITING_FOR_ACCEPTANCE,
    CANCELED,
    ACCEPTED;

    public Boolean canAccept() {
        return this.equals(WAITING_FOR_ACCEPTANCE);
    }

    public Boolean canCancel() {
        return this.equals(WAITING_FOR_ACCEPTANCE);
    }
}
