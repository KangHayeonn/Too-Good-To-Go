package com.toogoodtogo.web.shops;

import com.toogoodtogo.domain.shop.Hours;
import com.toogoodtogo.domain.shop.Shop;
import lombok.Getter;

@Getter
public class ShopDto {
    private final Long id;
    private final String name;
    private final String image;
    private final String[] category;
    private final String phone;
    private final String address;
    private final Hours hours;

    public ShopDto(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.image = shop.getImage();
        this.category = shop.getCategory();
        this.phone = shop.getPhone();
        this.address = shop.getAddress();
        this.hours = shop.getHours();
    }
}
