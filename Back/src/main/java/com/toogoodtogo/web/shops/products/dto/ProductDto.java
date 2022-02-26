package com.toogoodtogo.web.shops.products.dto;

import com.toogoodtogo.domain.shop.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDto {
    private Long shopId;
    private String shopName;
    private Long id;
    private String name;
    private Long price;
    private Long discountedPrice;
    private String image;

    @Builder
    public ProductDto(Long shopId, String shopName, Long id, String name, Long price, Long discountedPrice, String image) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.image = image;
    }

    @Builder
    public ProductDto(Product product) {
        this.shopId = product.getShop().getId();
        this.shopName = product.getShop().getName();
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.discountedPrice = product.getDiscountedPrice();
        this.image = product.getImage();
    }
}
