package com.toogoodtogo.web.orders.dto;

import com.toogoodtogo.application.order.AddOrderDto;
import lombok.Getter;

@Getter
public class AddOrderRequestProductDto {
    private long productId;
    private int quantity;

    public AddOrderDto.AddOrderProductDto convert() {
        return new AddOrderDto.AddOrderProductDto(productId, quantity);
    }
}
