package com.toogoodtogo.web.shops.shopboard;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.product.Product;
import lombok.Getter;

@Getter
public class ShopBoardDto {

    private final Long id;
    private final Shop shop;
    private final String name;
    private final Long price;
    private final Long discountedPrice;
    private final String image;

    public ShopBoardDto(Product product) {
        this.id = product.getId();
        this.shop = product.getShop();
        this.name = product.getName();
        this.price = product.getPrice();
        this.discountedPrice = product.getDiscountedPrice();
        this.image = product.getImage();
    }
}
