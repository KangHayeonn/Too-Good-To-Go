package com.toogoodtogo.application.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AddOrderDto {
    private String phone;
    private String paymentMethod;
    private String requirement;
    private List<AddOrderProductDto> products;

    @Getter
    @AllArgsConstructor
    public static class AddOrderProductDto {
        private long productId;
        private int quantity;
    }
}
