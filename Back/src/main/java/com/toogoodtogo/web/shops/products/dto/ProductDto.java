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
    private Long priority;

    @Builder
    public ProductDto(Long shopId, String shopName, Product product) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.discountedPrice = product.getDiscountedPrice();
        this.image = product.getImage();
        this.priority = product.getPriority();
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
        this.priority = product.getPriority();
    }
}
