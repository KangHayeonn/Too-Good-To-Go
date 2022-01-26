package com.toogoodtogo.application.order;

import com.toogoodtogo.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AddOrderDto {
    private User user;
    private String phone;
    private String paymentMethod;
    private String requirement;
    private List<AddOrderProductDto> products;
    private Boolean cacheRequirement;
    private Boolean cachePaymentMethod;

    @Getter
    @AllArgsConstructor
    public static class AddOrderProductDto {
        private long productId;
        private int quantity;
    }
}
