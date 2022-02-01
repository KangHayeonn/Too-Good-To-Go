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
    private List<AddOrderRequestProductDto> products;
    private Boolean cacheRequirement;
    private Boolean cachePaymentMethod;

    public AddOrderDto convert(User user) {
        return new AddOrderDto(
                user,
                phone,
                paymentMethod,
                requirement,
                products.stream()
                        .map(AddOrderRequestProductDto::convert)
                        .collect(Collectors.toList()),
                cacheRequirement,
                cachePaymentMethod
        );
    }
}
