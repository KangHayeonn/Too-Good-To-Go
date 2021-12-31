package com.toogoodtogo.web.shops.products;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.product.Product;
import lombok.Getter;

@Getter
public class ProductDto {
    private final Long id;
    private final String name;
    private final Long price;
    private final Long discountedPrice;
    private final String image;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.discountedPrice = product.getDiscountedPrice();
        this.image = product.getImage();
    }
}
