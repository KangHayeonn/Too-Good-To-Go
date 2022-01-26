package com.toogoodtogo.web.orders.dto;

import com.toogoodtogo.domain.order.CachedOrderInfo;
import lombok.Getter;

@Getter
public class GetCachedOrderInfoResponse {
    private final String requirement;
    private final String paymentMethod;

    public GetCachedOrderInfoResponse(CachedOrderInfo cachedOrderInfo) {
        requirement = cachedOrderInfo.getRequirement();
        paymentMethod = cachedOrderInfo.getPaymentMethod();
    }
}
