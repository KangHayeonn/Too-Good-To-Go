package com.toogoodtogo.web.shops.products.dto;

import com.toogoodtogo.domain.shop.product.Product;
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

    public ProductDto(Long shopId, String shopName, Product product) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.discountedPrice = product.getDiscountedPrice();
        this.image = product.getImage();
    }
}
