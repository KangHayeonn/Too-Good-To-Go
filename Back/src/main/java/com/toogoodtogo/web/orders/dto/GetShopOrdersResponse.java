package com.toogoodtogo.web.orders.dto;

import com.toogoodtogo.domain.order.Order;
import com.toogoodtogo.domain.order.OrderProduct;
import com.toogoodtogo.domain.order.OrderStatus;
import com.toogoodtogo.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetShopOrdersResponse {
    private final Long id;
    private final GetShopOrdersResponse.UserDto user;
    private final List<GetShopOrdersResponse.ProductDto> products;
    private final OrderStatus status;
    private final String requirement;
    private final LocalDateTime eta;
    private final LocalDateTime createdAt;

    @Getter
    public static class UserDto {
        private final Long id;
        private final String phone;

        public UserDto(User user) {
            id = user.getId();
            phone = user.getPhone();
        }
    }

    @Getter
    public static class ProductDto {
        private final Long id;
        private final Integer quantity;
        private final String name;
        private final Long price;
        private final Long discountedPrice;

        public ProductDto(OrderProduct orderProduct) {
            id = orderProduct.getId();
            quantity = orderProduct.getQuantity();
            name = orderProduct.getProduct().getName();
            price = orderProduct.getPrice();
            discountedPrice = orderProduct.getDiscountedPrice();
        }
    }

    public GetShopOrdersResponse(Order order) {
        id = order.getId();
        user = new GetShopOrdersResponse.UserDto(order.getUser());
        products = order.getOrderProducts().stream()
                .map(GetShopOrdersResponse.ProductDto::new)
                .collect(Collectors.toList());
        status = order.getStatus();
        requirement = order.getRequirement();
        eta = order.getEta();
        createdAt = order.getCreatedAt();
    }
}
