package com.toogoodtogo.web.orders.dto;

import com.toogoodtogo.application.order.AddOrderDto;
import com.toogoodtogo.domain.user.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AddOrderRequest {
    private String phone;
    private String requirement;
    private String paymentMethod;
    private Boolean needDisposables;
    private List<AddOrderRequestProductDto> products;
    private Boolean cacheRequirement;
    private Boolean cachePaymentMethod;

    @Getter
    public static class AddOrderRequestProductDto {
        private long productId;
        private int quantity;
        private long price;

        public AddOrderDto.AddOrderProductDto convert() {
            return new AddOrderDto.AddOrderProductDto(
                    productId, quantity, price);
        }
    }

    public AddOrderDto convert(User user) {
        return new AddOrderDto(
                user,
                phone,
                paymentMethod,
                requirement,
                needDisposables,
                products.stream()
                        .map(AddOrderRequestProductDto::convert)
                        .collect(Collectors.toList()),
                cacheRequirement,
                cachePaymentMethod
        );
    }
}
