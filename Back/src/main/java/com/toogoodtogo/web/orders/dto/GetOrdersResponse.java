package com.toogoodtogo.web.orders.dto;

import com.toogoodtogo.domain.order.Order;
import com.toogoodtogo.domain.order.OrderProduct;
import com.toogoodtogo.domain.order.OrderStatus;
import com.toogoodtogo.domain.shop.Shop;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetOrdersResponse {
    private final Long id;
    private final ShopDto shop;
    private final List<ProductDto> products;
    private final OrderStatus status;
    private final String requirement;
    private final LocalDateTime createdAt;

    @Getter
    public static class ShopDto {
        private final Long id;
        private final String name;
        private final List<String> categories;
        private final String phone;
        private final String image;

        public ShopDto(Shop shop) {
            id = shop.getId();
            name = shop.getName();
            categories = shop.getCategory();
            phone = shop.getPhone();
            image = shop.getImage();
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

    public GetOrdersResponse(Order order) {
        id = order.getId();
        shop = new ShopDto(order.getShop());
        products = order.getOrderProducts().stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
        status = order.getStatus();
        requirement = order.getRequirement();
        createdAt = order.getCreatedAt();
    }
}
