package com.toogoodtogo.web.shops;

import com.toogoodtogo.domain.shop.Shop;
import lombok.Getter;

@Getter
public class ShopDto {

    private final Long id;
    private final String name;
    private final String image;
    private final String[] category;

    public ShopDto(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.image = shop.getImage();
        this.category = shop.getCategory();
    }
}
