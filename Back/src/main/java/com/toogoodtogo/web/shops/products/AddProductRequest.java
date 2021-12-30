package com.toogoodtogo.web.shops.products;

import com.toogoodtogo.domain.shop.Shop;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddProductRequest {
    private Shop shop;
    private String name;
    private Long price;
    private Long discountedPrice;
    private String image;
}